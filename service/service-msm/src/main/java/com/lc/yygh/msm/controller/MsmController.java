package com.lc.yygh.msm.controller;

import com.lc.yygh.common.result.Result;
import com.lc.yygh.msm.service.MsmService;
import com.lc.yygh.msm.uitls.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/msm")
@Api(tags = "短信验证码管理")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送手机验证码
    @ApiOperation("发送手机验证码")
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        //验证码缓存到redis中，首先从redis中获取验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.success();
        }
        //redis中获取不到验证码，就要生成验证码
        code = RandomUtil.getSixBitRandom();
        //调用service的方法来发送验证码
        boolean b = msmService.sendMsm(phone, code);
        if (b) {
            //把验证码放到redis中，存储2分钟
            redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
            return Result.success();
        }
        return Result.build(509, "发送验证码失败");
    }
}