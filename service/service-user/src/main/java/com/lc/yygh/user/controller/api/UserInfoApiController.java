package com.lc.yygh.user.controller.api;

import com.lc.yygh.common.result.Result;
import com.lc.yygh.common.utils.AuthContextHolder;
import com.lc.yygh.model.user.UserInfo;
import com.lc.yygh.user.service.UserInfoService;
import com.lc.yygh.user.utlis.IpUtil;
import com.lc.yygh.vo.user.LoginVo;
import com.lc.yygh.vo.user.UserAuthVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("会员登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        loginVo.setIp(IpUtil.getIpAddr(request));
        Map<String, Object> map = userInfoService.login(loginVo);
        return Result.success(map);
    }

    @ApiOperation("会员测试")
    @GetMapping("/auth/test")
    public Result testLogin() {
        return Result.success("Success...");
    }

    //用户认证
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //获取用户id，将UserAuthVo对象中的数据更新到对应的用户记录中
        Long userId = AuthContextHolder.getUserId(request);
        //调用service中的方法，传入2个参数，参数1用户id,参数2是Vo对象
        userInfoService.userAuth(userId, userAuthVo);
        return Result.success();
}
    //获取用户信息
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo user = userInfoService.getById(userId);
        return Result.success(user);
    }
}