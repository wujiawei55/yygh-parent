package com.lc.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.yygh.common.exception.YyghException;
import com.lc.yygh.common.helper.JwtHelper;
import com.lc.yygh.common.result.ResultStatus;
import com.lc.yygh.enums.AuthStatusEnum;
import com.lc.yygh.model.user.Patient;
import com.lc.yygh.model.user.UserInfo;
import com.lc.yygh.model.user.UserLoginRecord;
import com.lc.yygh.user.mapper.UserInfoMapper;
import com.lc.yygh.user.mapper.UserLoginRecordMapper;
import com.lc.yygh.user.service.PatientService;
import com.lc.yygh.user.service.UserInfoService;
import com.lc.yygh.vo.user.LoginVo;
import com.lc.yygh.vo.user.UserAuthVo;
import com.lc.yygh.vo.user.UserInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private UserLoginRecordMapper recordMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PatientService patientService;
    @Override
    public Map<String, Object> login(LoginVo loginVo) {
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();
        //校验参数
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            throw new YyghException(ResultStatus.PARAM_ERROR);
        }
        // 验证码的校验
        String checkCode = redisTemplate.opsForValue().get(phone);
        if (!checkCode.equals(code)) {
            throw new YyghException(ResultStatus.CODE_ERROR);
        }
        //手机号是否已被使用
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        //获取会员
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);
        if (null == userInfo) {
            //第一次使用该手机号登录，让用户注册到系统中
            userInfo = new UserInfo();
            userInfo.setPhone(phone);
            userInfo.setName("");
            userInfo.setStatus(1);
            //保存起来
            this.save(userInfo);
        }
        if (userInfo.getStatus() == 0) {
            throw new YyghException(ResultStatus.LOGIN_DISABLED_ERROR);
        }
        //把登录信息记录下来，保存到user_login_record表中
        UserLoginRecord record = new UserLoginRecord();
        record.setUserId(userInfo.getId());
        record.setIp(loginVo.getIp());
        recordMapper.insert(record);

        //构造一个Map,封装需要返回给前端的数据
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);
        //TODO:生成Token串
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);
        return map;
    }

    @Override
    public void userAuth(Long userId, UserAuthVo userAuthVo) {
        //查找到用户记录
        UserInfo userInfo = baseMapper.selectById(userId);
        userInfo.setName(userAuthVo.getName());
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //更新用户
        baseMapper.updateById(userInfo);

    }

    @Override
    public IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo) {
        //UserInfoQueryVo获取条件值
        String name = userInfoQueryVo.getKeyword(); //用户名称关键字
        Integer status = userInfoQueryVo.getStatus();//用户状态
        Integer authStatus = userInfoQueryVo.getAuthStatus(); //认证状态
        String createTimeBegin = userInfoQueryVo.getCreateTimeBegin(); //开始时间
        String createTimeEnd = userInfoQueryVo.getCreateTimeEnd(); //结束时间
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(authStatus)) {
            wrapper.eq("auth_status", authStatus);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }
        Page<UserInfo> page = baseMapper.selectPage(pageParam, wrapper);
        page.getRecords().stream().forEach(item -> {
            this.packUserinfo(item);
        });
        return page;
    }

    //把编号转换为对应的字典名称
    private UserInfo packUserinfo(UserInfo userInfo) {
        String statusNameByStatus = AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus());
        //存储认证状态名称
        userInfo.getParam().put("authStatusString", statusNameByStatus);
        String statusString = userInfo.getStatus().intValue() == 0 ? "锁定" : "正常";
        userInfo.getParam().put("statusString", statusString);
        return userInfo;
    }

    @Override
    public void lock(Long id, Integer status) {
        UserInfo userInfo = baseMapper.selectById(id);
        userInfo.setStatus(status);
        //修改
        baseMapper.updateById(userInfo);
    }

    @Override
    public Map<String, Object> show(Long userId) {
        UserInfo userInfo =   this.packUserinfo( baseMapper.selectById(userId));
        //根据userid查询就诊人信息
        List<Patient> patients = patientService.findByUserId(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo",userInfo);
        map.put("patientList",patients);
        return map;
    }

    @Override
    public void approval(Long userId, Integer authStatus) {
        if(authStatus.intValue()==2 || authStatus.intValue()==-1) {
            UserInfo userInfo = baseMapper.selectById(userId);
            userInfo.setAuthStatus(authStatus);
            baseMapper.updateById(userInfo);
        }

    }
}
