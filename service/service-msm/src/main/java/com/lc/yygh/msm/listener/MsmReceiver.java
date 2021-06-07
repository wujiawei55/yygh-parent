package com.lc.yygh.msm.listener;

import com.lc.yygh.rabbit.constant.MqConst;
import com.lc.yygh.msm.service.MsmService;
import com.lc.yygh.vo.msm.MsmVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import java.nio.channels.Channel;

/**
 * ClassName MsmReceiver
 * Description  监听器(当监听到所绑定的的队列中有消息时，就执行对应的方法)
 * Create by lujun
 * Date 2021/5/15 15:37
 */
@Component
public class MsmReceiver {
    @Autowired
    private MsmService msmService;
    //监听到队列中有消息，自动执行方法
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
        msmService.send(msmVo);
    }
}
