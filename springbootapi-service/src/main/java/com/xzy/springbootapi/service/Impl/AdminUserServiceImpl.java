package com.xzy.springbootapi.service.Impl;

import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.mapper.AdminUserMapper;
import com.xzy.springbootapi.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xzy on 18/10/15  .
 */

@Service
public class AdminUserServiceImpl implements AdminUserService{
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Override
    public List<AdminUser> loadAll() {
        return adminUserMapper.loadAll();
    }
}
