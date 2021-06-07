package com.lc.yygh.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result {

    @ApiModelProperty(value = "响应状态码")
    private Integer code;
    @ApiModelProperty(value = "响应消息")
    private String message;
    @ApiModelProperty(value = "响应的数据")
    private Object data;

    private Result() {
    }

    public static Result build(Object data, ResultStatus resultStatus) {
        Result result = new Result();
        if (null != data) {
            result.setData(data);
        }
        result.setCode(resultStatus.getCode());
        result.setMessage(resultStatus.getMessage());
        return result;
    }

    public static Result build(Integer code,String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    /**
     * 操作成功且携带响应数据
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return build(data, ResultStatus.SUCCESS);
    }

    /**
     * 操作成功，不带响应数据
     *
     * @return
     */
    public static Result success() {
        return Result.success(null);
    }

    /**
     * 操作失败且携带响应数据
     *
     * @param data
     * @return
     */
    public static Result fail(Object data) {
        return build(data, ResultStatus.FAIL);
    }

    /**
     * 操作失败，不带响应数据
     *
     * @return
     */
    public static Result fail() {
        return Result.fail(null);
    }
}
