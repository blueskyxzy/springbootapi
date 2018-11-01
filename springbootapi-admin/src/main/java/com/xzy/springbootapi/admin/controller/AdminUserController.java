package com.xzy.springbootapi.admin.controller;


import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.service.AdminUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xzy on 18/11/1  .
 */

@RestController
public class AdminUserController extends BaseRestController{
    @Resource
    AdminUserService adminUserService;

    @GetMapping("/allAdminUsers")
    public void GetAllAdminUsers(HttpServletResponse response){
        List<AdminUser> adminUsers = adminUserService.loadAll();
        writeSuccess(response,adminUsers);
    }

    @GetMapping("/users/{id}")
    public void UserGet(HttpServletResponse response,
                        @PathVariable Long id){
        // TODO
    }

    @GetMapping("/users")
    public void UserList(HttpServletResponse response,
                         @RequestParam(value = "start", required = false) Long start,
                         @RequestParam(value = "limit", required = false)Long limit){
        // TODO
    }

    @PostMapping("/users")
    public void UserPost(HttpServletResponse response){
        // TODO
    }

    @DeleteMapping("/users/{id}")
    public void UserDelete(HttpServletResponse response,
                           @PathVariable Long id){
        // TODO
    }

    @PatchMapping("/users/{id}")
    public void UserPatch(HttpServletResponse response,
                           @PathVariable Long id){
        // TODO
    }
}
