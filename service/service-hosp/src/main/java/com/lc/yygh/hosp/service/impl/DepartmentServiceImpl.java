package com.lc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lc.yygh.hosp.repository.DepartmentRepository;
import com.lc.yygh.hosp.service.DepartmentService;
import com.lc.yygh.model.hosp.Department;
import com.lc.yygh.vo.hosp.DepartmentQueryVo;
import com.lc.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName DepartmentServiceImpl
 * Description
 * Create by lujun
 * Date 2021/4/28 16:09
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        String s = JSONObject.toJSONString(paramMap);
        //根据参数获取Department对象
        Department dep = JSONObject.parseObject(s, Department.class);
        //从数据库中查找对应的Department对象
        Department department = departmentRepository.findByHoscodeAndDepcode(dep.getHoscode(), dep.getDepcode());
        if (null != department) {
            //科室已存在，做更新
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        } else {
            //插入一个新科室
            Date now = new Date();
            dep.setCreateTime(now);
            dep.setUpdateTime(now);
            dep.setIsDeleted(0);
            departmentRepository.save(dep);
        }

    }

    @Override
    public Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //封装一个Page对象
        Pageable pageable = PageRequest.of(page - 1, limit);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写

        Department department = new Department();
        //拷贝属性的值
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);
        Example<Department> example = Example.of(department, matcher);

        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.findByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建List集合，封装最终数据
        List<DepartmentVo> list = new ArrayList<>();
        //根据医院编号，查询医院所有科室信息
        Department department = new Department();
        department.setHoscode(hoscode);
        Example example = Example.of(department);
        //所有科室列表 departmentList
        List<Department> departmentList = departmentRepository.findAll(example);
        //利用bigCode分组
        Map<String, List<Department>> deparmentMap =
                departmentList.stream().collect(Collectors.groupingBy(e -> e.getBigcode()));
        for (Map.Entry<String, List<Department>> entry : deparmentMap.entrySet()) {
            //获取大科室的编码
            String bigcode = entry.getKey();
            //大科室编号对应的科室列表
            List<Department> deptList = entry.getValue();
            //封装大科室
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(bigcode);
            departmentVo.setDepname(deptList.get(0).getBigname());
            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for (Department dept : deptList) {
                DepartmentVo deptVo = new DepartmentVo();
                deptVo.setDepcode(dept.getDepcode());
                deptVo.setDepname(dept.getDepname());
                children.add(deptVo);
            }
            departmentVo.setChildren(children);
            //添加大科室到集合list中
            list.add(departmentVo);
        }
        return list;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        Department dept = departmentRepository.findByHoscodeAndDepcode(hoscode, depcode);
        return dept.getDepname();
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        Department department = departmentRepository.findByHoscodeAndDepcode(hoscode, depcode);
        return department;
    }
}
