package com.wsl.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String userName;
    private String password;

    public User() {
    }

    public User(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
