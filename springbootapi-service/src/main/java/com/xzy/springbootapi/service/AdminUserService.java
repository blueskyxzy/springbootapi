package com.xzy.springbootapi.service;

import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.vo.AdminUserVo;

import java.util.List;

public interface AdminUserService {

    List<AdminUser> loadAll();

    List<AdminUserVo> selectAdminList(Long start, Long limit);

    int insertAdminUser(AdminUser adminUser);

    AdminUser selectByMobile(String mobile);
}
