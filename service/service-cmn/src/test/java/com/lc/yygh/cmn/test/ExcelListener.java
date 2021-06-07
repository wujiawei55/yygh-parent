package com.lc.yygh.cmn.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * 自定义的监听器(回调监听器)
 */
public class ExcelListener extends AnalysisEventListener<Employee> {

    //每一条数据解析都会来调用invoke()。一行一行读取excel内容，从第二行开始读取
    @Override
    public void invoke(Employee emp, AnalysisContext analysisContext) {
        System.out.println(emp);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //所有数据解析完成了，就调用doAfterAllAnalysed()
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完成了...");
    }
}
