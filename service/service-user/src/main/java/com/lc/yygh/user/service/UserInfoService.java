package com.lc.yygh.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.yygh.model.user.UserInfo;
import com.lc.yygh.vo.user.LoginVo;
import com.lc.yygh.vo.user.UserAuthVo;
import com.lc.yygh.vo.user.UserInfoQueryVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    //用户登录
    Map<String, Object> login(LoginVo loginVo);
   //用户认证
    void userAuth(Long userId, UserAuthVo userAuthVo);

    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    //锁定(解锁)
    void lock(Long id,Integer status);
   //根据id获取用户详情
    Map<String, Object> show(Long userId);

    void approval(Long userId, Integer authStatus);
}
