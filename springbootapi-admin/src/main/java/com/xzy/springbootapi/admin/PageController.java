package com.xzy.springbootapi.admin;

import com.xzy.springbootapi.admin.controller.BaseRestCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xzy on 18/10/24  .
 */

@Controller
@RequestMapping("/p")
public class PageController extends BaseRestCtrl{

    // 返回页面
    @RequestMapping("/login")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
}
