package de.fhaachen.ipdashboard.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    private String authName;

    public Role(String role) {
        this.authName = role;
    }

    @Override
    public String getAuthority() {
        return this.authName;
    }



}