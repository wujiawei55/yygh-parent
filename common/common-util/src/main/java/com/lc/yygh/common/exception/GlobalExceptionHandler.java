package com.lc.yygh.common.exception;

import com.lc.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName GlobalExceptionHandler
 * Description 全局异常处理器
 *  @RestControllerAdvice= @ControllerAdvice+@ResponseBody
 *
 * Create by lujun
 * Date 2021/4/17 14:57
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    //当Controller中的方法抛出了Exception异常时,就会调用被
    //@ExceptionHandler所标注的error方法，返回json格式的数据
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }
    //当Controller中的方法抛出了YyghException异常时,就调用下面的error()
    @ExceptionHandler(YyghException.class)
    public Result error(YyghException e){
        Result result=Result.build(e.getCode(), e.getMessage());
        return result;
    }
}
