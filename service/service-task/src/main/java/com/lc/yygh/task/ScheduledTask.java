package com.lc.yygh.task;

import com.lc.yygh.rabbit.constant.MqConst;
import com.lc.yygh.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName ScheduledTask
 * Description
 * Create by lujun
 * Date 2021/5/17 10:55
 */
@Component
@EnableScheduling
public class ScheduledTask {
    @Autowired
    private RabbitService rabbitService;

    /**
     * 0/30 * * * * ?：每隔30秒执行一次
     * 0 0 8 * * ? ：每天早上8点执行的cron表达式
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void task1() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "");
    }
}
