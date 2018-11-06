package com.xzy.springbootapi.admin.mq;

/**
 * Created by xzy on 18/11/5  .
 */

//import com.xzy.springbootapi.service.consts.ServiceConsts;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.aliyun.openservices.ons.api.Action;
//import com.aliyun.openservices.ons.api.ConsumeContext;
//import com.aliyun.openservices.ons.api.Message;
//import com.aliyun.openservices.ons.api.MessageListener;
//
//import java.util.Date;

public class MyConsumerListener{

}
//public class MyConsumerListener implements MessageListener {
//
//    private static final Logger logger = LoggerFactory.getLogger(MyConsumerListener.class);
//
//    @Override
//    public Action consume(Message message, ConsumeContext context) {
//        try {
//
//            Boolean result = comsumerMessage(message);
//            if (result) {
//                return Action.CommitMessage;
//            } else {
//                return Action.ReconsumeLater;
//            }
//        } catch (Exception e) {
//            logger.error("Exception: {}", e);
//            return Action.ReconsumeLater;
//        }
//    }
//
//    private Boolean comsumerMessage(Message message) throws Exception {
//        String strId = new String(message.getBody());
//        System.out.println(new Date() + " get mq message success. tag is:" + message.getTag() + ", message body is: " + strId);
//
//        // 1. 测试的消息
//        if (ServiceConsts.AliMQMessage.testTAG.equalsIgnoreCase(message.getTag())){
//            System.out.println("-----:" + message.getTag() + "------");
//            logger.debug("testMQ  body: {}", strId);
//            return true;
//        }
//
//        // 2. 打印的消息
//        if (ServiceConsts.AliMQMessage.logTAG.equalsIgnoreCase(message.getTag())){
//            System.out.println("-----:" + message.getTag() + "------");
//            logger.debug("logMQ  body: {}", strId);
//            return true;
//        }
//
//        return false;
//    }
//
//}

