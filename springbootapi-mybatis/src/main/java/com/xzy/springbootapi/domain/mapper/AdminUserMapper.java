package com.xzy.springbootapi.domain.mapper;

import com.xzy.springbootapi.domain.AdminUser;

import java.util.List;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    List<AdminUser> loadAll();

}