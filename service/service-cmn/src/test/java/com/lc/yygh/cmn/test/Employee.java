package com.lc.yygh.cmn.test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName Employee
 * Description
 * Create by lujun
 * Date 2021/4/24 17:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @ExcelProperty(value = "编号",index=0)
    private Integer id;
    @ExcelProperty(value = "姓名",index=1)
    private String name;
    @ExcelProperty(value = "年龄",index=2)
    private Integer age;

}
