package com.lc.yygh.oss.api;

import com.lc.yygh.common.result.Result;
import com.lc.yygh.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName FileApiController
 * Description 文件上传
 * Create by lujun
 * Date 2021/5/10 21:23
 */
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {
    @Autowired
    private FileService fileService;
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) {
      String url=   fileService.upload(file);
      return Result.success(url);
    }
}