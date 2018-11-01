package com.xzy.springbootapi.service.Impl;

import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.mapper.AdminUserMapper;
import com.xzy.springbootapi.domain.vo.AdminUserVo;
import com.xzy.springbootapi.service.AdminUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public List<AdminUserVo> selectAdminList(Long start, Long limit) {
        List<AdminUserVo> adminUserVos = new ArrayList<>();
        List<AdminUser> adminList = adminUserMapper.selectAdminList(start, limit);
        for (AdminUser adminUser: adminList){
            AdminUserVo adminUserVo = new AdminUserVo();
            BeanUtils.copyProperties(adminUser, adminUserVo);
            adminUserVos.add(adminUserVo);
        }
        return adminUserVos;
    }
}
