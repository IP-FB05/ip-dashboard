package de.fhaachen.ipdashboard.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class User{
    
    private String username;
    private String password;
    private String name;
    // private String firstName;
    // private String lastName;
    private String email;
    private String[] groups;
    private List<GrantedAuthority> authorities;

    public User() {
    }

    public User(String name, String username, String email, String password) {
        this.setName(name);
        this.setUsername(username);
        this.email = email;
        this.setPassword(password);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * @return the authorities
     */
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * @return the groups
     */
    public String[] getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    } 

    public String toString() {
        return this.name + " (" + this.email + ")";
    }

}