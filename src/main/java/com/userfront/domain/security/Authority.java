package com.userfront.domain.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by maripen on 2016. 11. 13..
 */
public class Authority implements GrantedAuthority {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
