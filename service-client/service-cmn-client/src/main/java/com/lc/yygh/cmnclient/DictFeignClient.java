package com.lc.yygh.cmnclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName DictFiegnClient
 * Description
 * Create by lujun
 * Date 2021/4/29 15:43
 */
@FeignClient("service-cmn")
public interface DictFeignClient {
    //直接把要调用的service-cmn服务中的接口声明拷贝过来
    @GetMapping(value = "/admin/cmn/dict/getName/{parentDictCode}/{value}")
    public String getName(@PathVariable("parentDictCode") String parentDictCode,
                          @PathVariable("value") String value);

    @GetMapping(value = "/admin/cmn/dict/getName/{value}")
    public String getName(
            @PathVariable("value") String value);
}
