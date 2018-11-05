package com.xzy.springbootapi.admin.controller;


import com.xzy.springbootapi.admin.utils.RequestData;
import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.model.PageResultVo;
import com.xzy.springbootapi.domain.vo.AdminUserVo;
import com.xzy.springbootapi.service.AdminUserService;
import com.xzy.springbootapi.service.utils.RedisKeyUtil;
import com.xzy.springbootapi.service.utils.RedisService;
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
public class AdminUserController extends BaseRestController{
    @Resource
    AdminUserService adminUserService;

    @Resource
    RedisService redisService;

    @GetMapping("/users/{id}")
    public void UserGet(HttpServletResponse response,
                        @PathVariable Long id){
        // TODO
    }

    @GetMapping("/users")
    public void UserList(HttpServletResponse response,
                         @ApiParam(value = "起始数") Long start,
                         @ApiParam(value = "结束时") @RequestParam(value = "limit", required = false) Long limit){
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

    @PostMapping("/users/setRedis")
    public void UserRedisPost(HttpServletResponse response,
                         @RequestBody RequestData<AdminUser> reqData){
        // 为省时间,简写
        AdminUser adminUser = reqData.getData();
        redisService.setOpsForValue("adminUser", String.valueOf(adminUser.getName()));
        String adminUserName = redisService.getOpsForValue("adminUser");
        writeSuccess(response, adminUserName);
    }
}
