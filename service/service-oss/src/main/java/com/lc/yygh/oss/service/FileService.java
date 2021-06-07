package com.lc.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName FileService
 * Description
 * Create by lujun
 * Date 2021/5/10 21:25
 */
public interface FileService {
    //上传文件
    String upload(MultipartFile file);
}
