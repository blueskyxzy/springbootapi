package xzy.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xzy on 18/10/12  .
 */

@RestController
public class TestController {

    @RequestMapping("/")
    public String home(){
        return "hello world";
    }
}
