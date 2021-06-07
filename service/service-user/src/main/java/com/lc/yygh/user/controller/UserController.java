package com.lc.yygh.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.yygh.common.result.Result;
import com.lc.yygh.model.user.UserInfo;
import com.lc.yygh.user.service.UserInfoService;
import com.lc.yygh.vo.user.UserInfoQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName UserController
 * Description
 * Create by lujun
 * Date 2021/5/10 22:59
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> pageParam =new Page<>(page,limit);
        IPage<UserInfo> pageModel =  userInfoService.selectPage(pageParam,userInfoQueryVo);
      return Result.success(pageModel);
    }

    @ApiOperation(value = "锁定")
    @PutMapping("lock/{userId}/{status}")
    public Result lock(
            @PathVariable("userId") Long userId,
            @PathVariable("status") Integer status){
        userInfoService.lock(userId,status);
        return Result.success();
    }

    //用户详情
    @GetMapping("show/{userId}")
    public Result getDetail(@PathVariable Long userId){
      Map<String,Object> map=  userInfoService.show(userId);
      return Result.success(map);
    }
    //用户认证审批
    @PutMapping("approval/{userId}/{authStatus}")
    public Result  approval(@PathVariable Long userId,@PathVariable Integer authStatus){
        userInfoService.approval(userId,authStatus);
        return Result.success();
    }
}
