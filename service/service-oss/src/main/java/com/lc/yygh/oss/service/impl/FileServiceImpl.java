package com.lc.yygh.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lc.yygh.oss.service.FileService;
import com.lc.yygh.oss.utils.ConstantOssPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * ClassName FileServiceImpl
 * Description
 * Create by lujun
 * Date 2021/5/10 21:27
 */
@Service
public class FileServiceImpl  implements FileService {
    @Override
    public String upload(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantOssPropertiesUtil.EDNPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId =ConstantOssPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret=ConstantOssPropertiesUtil.SECRECT;
        String  bucketName= ConstantOssPropertiesUtil.BUCKET;
        OSS ossClient =null;
        try {
// 创建OSSClient实例。
             ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream =file.getInputStream();
   // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            //获取上传文件的原始名称
            String filename = file.getOriginalFilename();
            //生成随机唯一值，使用uuid，添加到文件名称里面
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            filename=uuid+filename;
             //使用日期时间工具
            String s = new DateTime().toString("yyyy/MM/dd");
            filename=  s+"/"+filename;
            ossClient.putObject(bucketName, filename, inputStream);
            //返回文件的完整url,形如：https://yygh-lcxy.oss-cn-beijing.aliyuncs.com/avatar6.png
             String url=String.format("https://%s.%s/%s",bucketName,endpoint,filename);
             return url;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return null;

    }
}
