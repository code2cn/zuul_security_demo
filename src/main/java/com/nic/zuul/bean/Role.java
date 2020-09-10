package com.nic.zuul.bean;

import org.springframework.security.core.GrantedAuthority;

public class Role{
    private String role;
    private String roleZh;

    public String getRoleZh() {
        return roleZh;
    }

    public void setRoleZh(String roleZh) {
        this.roleZh = roleZh;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
