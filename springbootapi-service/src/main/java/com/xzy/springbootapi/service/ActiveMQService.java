package com.xzy.springbootapi.service;

public interface ActiveMQService {

    void sendMsg();

    void receiveMsg(String msg);
}
