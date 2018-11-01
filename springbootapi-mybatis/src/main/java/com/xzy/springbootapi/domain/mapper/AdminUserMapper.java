package com.xzy.springbootapi.domain.mapper;

import com.xzy.springbootapi.domain.AdminUser;
import com.xzy.springbootapi.domain.vo.AdminUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    AdminUser selectByMobile(@Param("mobile") String mobile);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    List<AdminUser> loadAll();

    List<AdminUser> selectAdminList(@Param("start") Long start,@Param("limit") Long limit);
}