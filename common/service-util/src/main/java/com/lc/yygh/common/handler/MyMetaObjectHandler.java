package com.lc.yygh.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName MyMetaObjectHandler
 * Description
 * Create by lujun
 * Date 2021/4/14 20:14
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //进行insert操作时，自动执行这个方法
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        //填充version字段
        // this.setFieldValByName("version",1,metaObject);
    }

    //进行update操作时，自动执行这个方法
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
