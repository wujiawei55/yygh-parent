package com.lc.yygh.cmn.test;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName ExcelWriteTest
 * EasyExcel实现写操作，导出数据到文件
 * Create by lujun
 * Date 2021/4/24 17:23
 */
public class ExcelWriteTest {
    public static void main(String[] args) {
        List<Employee>  list=new ArrayList<>();
        list.add(new Employee(1, "小明", 30));
        list.add(new Employee(2, "小刚", 22));
        list.add(new Employee(3, "小美", 18));
        String pathName="D:/test/01.xlsx";
        EasyExcel.write(pathName,Employee.class).sheet("员工信息表").doWrite(list);
        System.out.println("导出成功");
    }
}
