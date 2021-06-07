package com.lc.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.yygh.cmn.listener.DictListener;
import com.lc.yygh.cmn.mapper.DictMapper;
import com.lc.yygh.cmn.service.DictService;
import com.lc.yygh.model.cmn.Dict;
import com.lc.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName DictServiceImpl
 * Description
 * Create by lujun
 * Date 2021/4/23 19:28
 */
@Service
@Transactional
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    //根据数据id查询子数据列表
    // @Cacheable(value = "dict",key="#root.methodName")
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    @Override
    public List<Dict> findChlidData(Long id) {
        //假设id是1，要构造一个select 字段列表 from dict where parent_id=1
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向list集合每个dict对象中设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    //根据id判断数据下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        //selectCount()构造select count(*) from dict where parent_id=1
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("数据字典", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            List<Dict> dictList = baseMapper.selectList(null);
            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
            for (Dict dict : dictList) {
                DictEeVo dictVo = new DictEeVo();
                //dictVo.setId(dict.getId());
                //复制属性的值
                BeanUtils.copyProperties(dict, dictVo, DictEeVo.class);
                dictVoList.add(dictVo);
            }

            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importData(MultipartFile file) {
        //Excel读操作
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getNameByParentDictCodeAndValue(String parentDictCode, String value) {
        //如果value能唯一定位数据字典，parentDictCode可以传空，例如：省市区的value值能够唯一确定字典

        if (StringUtils.isEmpty(parentDictCode)) {
            //根据parentDictCode查到对应的上级id
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.select("name").eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        } else {
//             QueryWrapper<Dict> wrapper=new QueryWrapper<>();
//             wrapper.select("id").eq("dict_code",parentDictCode);
//             Dict dict = baseMapper.selectOne(wrapper);
            Long id = this.getIdByDictCode(parentDictCode);
            Dict child = baseMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", id).eq("value", value));
            return child.getName();
        }

    }

    //根据节点dictCode获取节点的id
    private Long getIdByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.select("id").eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        return dict.getId();
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
         //得到节点的id
        Long id = this.getIdByDictCode(dictCode);
        List<Dict> chlidData = this.findChlidData(id);
        return chlidData;
    }
}
