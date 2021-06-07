package com.lc.yygh.hosp.service;

import com.lc.yygh.model.hosp.Department;
import com.lc.yygh.vo.hosp.DepartmentQueryVo;
import com.lc.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * ClassName DepartmentService
 * Description
 * Create by lujun
 * Date 2021/4/28 16:08
 */

/**
 * 科室业务接口
 */
public interface DepartmentService {
    //上传科室
    void save(Map<String, Object> paramMap);
    //分页查询科室
    Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo);
    //删除科室
    void remove(String hoscode, String depcode);
    //根据医院编号，查询医院所有科室列表
    List<DepartmentVo> findDeptTree(String hoscode);
     //获取科室名称
    String getDepName(String hoscode, String depcode);
   //根据医院编码和科室编码获取科室
    Department getDepartment(String hoscode, String depcode);
}
