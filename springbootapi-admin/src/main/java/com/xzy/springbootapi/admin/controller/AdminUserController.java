package com.xzy.springbootapi.admin.controller;

import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xzy on 18/10/24  .
 */

@Controller
@RequestMapping("/user")
public class AdminUserController extends BaseRestCtrl{

    @Autowired
    private AdminUserService adminUserService;

    // 返回页面
    @RequestMapping("/home")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/allAdminUsers")
    public void testApi(HttpServletResponse response){
        List<AdminUser> adminUsers = adminUserService.loadAll();
        writeSuccess(response,adminUsers);
    }
}
