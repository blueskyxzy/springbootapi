package com.xzy.springbootapi.admin.controller;

import com.xzy.springbootapi.admin.controller.BaseRestCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xzy on 18/10/24  .
 */

@Controller
@RequestMapping("/p")
public class PageController{

    // 返回页面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
}
