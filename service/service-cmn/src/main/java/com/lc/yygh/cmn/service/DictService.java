package com.lc.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName DictService
 * Description
 * Create by lujun
 * Date 2021/4/23 19:27
 */
public interface DictService extends IService<Dict> {
    //根据数据id查询子数据列表
    List<Dict> findChlidData(Long id);
    //导出数据字典
    void exportData(HttpServletResponse  response);
    //导入数据字典
    void importData(MultipartFile file);

    /**
     * 根据上级编码与值获取数据字典名称
     * @param parentDictCode
     * @param value
     * @return
     */
    String getNameByParentDictCodeAndValue(String parentDictCode, String value);
    //根据dictCode查询下级节点
    List<Dict> findByDictCode(String dictCode);
}
