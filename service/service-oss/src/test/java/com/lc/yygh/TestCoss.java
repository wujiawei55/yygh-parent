package com.lc.yygh;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

/**
 * ClassName test
 * Description
 * Create by lujun
 * Date 2021/5/10 21:15
 */
public class TestCoss {
    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
     // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI5tCkTVdXDyyuEzVmK1eV";
        String accessKeySecret = "VmWzRehgz1uG9iSg4EX81PR40Rwmcu";
        String bucketName = "yygh-osstest2";

       // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

     // 创建存储空间。
        ossClient.createBucket(bucketName);

      // 关闭OSSClient。
        ossClient.shutdown();
    }
}
