package com.lc.yygh.common.exception;

import com.lc.yygh.common.result.ResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName YyghException
 * Description 自定义全局异常类
 * Create by lujun
 * Date 2021/4/16 17:06
 */
@Data
@ApiModel("自定义全局异常类")
public class YyghException extends RuntimeException {

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 接收枚举对象，创建异常对象
     *
     * @param resultStatus
     */
    public YyghException(ResultStatus resultStatus) {
        //设置异常消息
        super(resultStatus.getMessage());
        this.code = resultStatus.getCode();
    }


    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public YyghException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    @Override
    public String toString() {
        return "YyghException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }

}
