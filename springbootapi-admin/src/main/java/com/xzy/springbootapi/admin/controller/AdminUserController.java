package com.xzy.springbootapi.admin.controller;


import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.model.PageResultVo;
import com.xzy.springbootapi.domain.vo.AdminUserVo;
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

    @GetMapping("/users/{id}")
    public void UserGet(HttpServletResponse response,
                        @PathVariable Long id){
        // TODO
    }

    @GetMapping("/users")
    public void UserList(HttpServletResponse response,
                         @RequestParam(value = "start", required = false) Long start,
                         @RequestParam(value = "limit", required = false) Long limit){
        if (start == null && limit == null){
            List<AdminUser> adminUsers = adminUserService.loadAll();
            writeSuccess(response, adminUsers);
        } else {
            List<AdminUserVo> adminList = adminUserService.selectAdminList(start, limit);
            PageResultVo<AdminUserVo> pageResultVo = new PageResultVo<>();
            pageResultVo.setReturnList(adminList);
            pageResultVo.setStart(start);
            pageResultVo.setLimit(limit);
            pageResultVo.setReturnCount((long)adminList.size());
            pageResultVo.setTotalCount((long)adminUserService.selectAdminList(null, null).size());
            writeSuccess(response, pageResultVo);
        }

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
