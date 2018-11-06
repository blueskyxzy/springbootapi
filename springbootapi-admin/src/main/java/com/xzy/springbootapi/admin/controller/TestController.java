package com.xzy.springbootapi.admin.controller;

import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.service.ActiveMQService;
import com.xzy.springbootapi.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xzy on 18/10/12  .
 */

@RestController
@RequestMapping("/test")
public class TestController extends BaseRestController {

    @Autowired
    private AdminUserService adminUserService;

    @Resource
    private ActiveMQService activeMQService;

    // 返回字符串
    @RequestMapping("/home")
    public String home(){
        return "hello world";
    }

    @GetMapping("/allAdminUsers")
    public void testApi(HttpServletResponse response){
        List<AdminUser> adminUsers = adminUserService.loadAll();
        writeSuccess(response,adminUsers);
    }

    @GetMapping("/activemq")
    public void testActiveMQ(HttpServletResponse response){
        activeMQService.sendMsg();
        writeSuccess(response, "发送成功");
    }
}
