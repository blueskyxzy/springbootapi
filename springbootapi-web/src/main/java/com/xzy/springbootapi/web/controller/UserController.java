package com.xzy.springbootapi.web.controller;


import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.model.PageResultVo;
import com.xzy.springbootapi.domain.vo.AdminUserVo;
import com.xzy.springbootapi.service.AdminUserService;
import com.xzy.springbootapi.web.utils.RequestData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xzy on 18/11/1  .
 */

@RestController
@Api(value = "客户端人员", description = "客户端人员")
public class UserController extends BaseRestController{
    @Resource
    AdminUserService adminUserService;

    @PostMapping("/users")
    public void UserPost(HttpServletResponse response,
                         @RequestBody RequestData<AdminUser> reqData){
        // 为省时间,简写
        AdminUser adminUser = reqData.getData();
        AdminUser adminUserDemo = adminUserService.selectByMobile(adminUser.getMobile());
        if (adminUserDemo != null){
            writeError(response, "手机号已注册");
            return;
        }
        int result = adminUserService.insertAdminUser(adminUser);
        if (result <= 0){
            writeError(response, "添加用户失败");
            return;
        }
        writeSuccess(response, "用户添加成功");
    }

}
