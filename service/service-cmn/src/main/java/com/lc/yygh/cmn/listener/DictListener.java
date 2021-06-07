package com.lc.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lc.yygh.cmn.mapper.DictMapper;
import com.lc.yygh.model.cmn.Dict;
import com.lc.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * ClassName DictListener
 * 监听器
 * Create by lujun
 * Date 2021/4/25 17:06
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {
    private DictMapper dictMapper;
    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }
    //每一条数据解析都会来调用invoke()。一行一行读取excel内容，从第二行开始读取
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict  dict=new Dict();
        //复制属性
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
