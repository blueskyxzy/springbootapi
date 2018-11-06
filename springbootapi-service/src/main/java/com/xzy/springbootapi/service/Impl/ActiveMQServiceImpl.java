package com.xzy.springbootapi.service.Impl;

/**
 * Created by xzy on 18/11/6  .
 */

import com.xzy.springbootapi.service.ActiveMQService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Topic;

@Service
public class ActiveMQServiceImpl implements ActiveMQService {

    @Resource
    JmsMessagingTemplate jmsMessagingTemplate;

    @Resource
    Topic topic;

    @Override
    public void sendMsg() {
        jmsMessagingTemplate.convertAndSend(topic, "createDevice");
    }

    @Override
    @JmsListener(destination = "xzyTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveMsg(String msg) {
        System.out.println("get message success:" + new String(msg.getBytes()));
    }


}
