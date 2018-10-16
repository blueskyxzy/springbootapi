package com.xzy.springbootapi.service;

import com.xzy.springbootapi.domain.AdminUser;

import java.util.List;

public interface AdminUserService {

    List<AdminUser> loadAll();

}
