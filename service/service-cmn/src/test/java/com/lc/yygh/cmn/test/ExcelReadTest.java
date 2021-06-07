package com.lc.yygh.cmn.test;

import com.alibaba.excel.EasyExcel;

/**
 * ClassName ExcelWriteTest
 * EasyExcel实现读操作
 * Create by lujun
 * Date 2021/4/24 17:23
 */
public class ExcelReadTest {
    public static void main(String[] args) {
         //要读取的文件
        String pathName="D:/test/01.xlsx";
       //调用方法来读数据
        EasyExcel.read(pathName,Employee.class,new ExcelListener()).sheet(1).doRead();
    }
}
