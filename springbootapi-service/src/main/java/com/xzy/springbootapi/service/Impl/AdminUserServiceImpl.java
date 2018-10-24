package com.xzy.springbootapi.service.Impl;

import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.mapper.AdminUserMapper;
import com.xzy.springbootapi.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xzy on 18/10/15  .
 */

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService{

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public List<AdminUser> loadAll() {
        return adminUserMapper.loadAll();
    }
}
