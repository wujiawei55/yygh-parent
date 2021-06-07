package com.lc.yygh.hosp.repository;

import com.lc.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ClassName DepartmentRepository
 * Description
 * Create by lujun
 * Date 2021/4/28 16:10
 */
public interface DepartmentRepository  extends MongoRepository<Department,String> {
       //查找一个科室
       Department findByHoscodeAndDepcode(String hosCode,String depCode);
}
