package com.xzy.springbootapi.domain.vo;

import java.util.Date;

/**
 * Created by xzy on 18/11/1  .
 */

public class AdminUserVo {

    private Long id;

    private String name;

    private String mobile;

    private String password;

    private Boolean locked;

    private Long incubatorId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Long getIncubatorId() {
        return incubatorId;
    }

    public void setIncubatorId(Long incubatorId) {
        this.incubatorId = incubatorId;
    }

}
