package de.fhaachen.ipdashboard.security.services;

import de.fhaachen.ipdashboard.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

    private String name;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(String name, 
			    		String username, String email, String password, 
			    		Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        String[] userGroups = user.getGroups();
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Hier wäre die vorherige Benennung der Gruppen in Camunda sinnvoller
        for(int i = 0; i < userGroups.length; i++) {
            switch (userGroups[i]) {
                case "admin":  
                        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        break;
                case "pruefungsamt":  
                        authorities.add(new SimpleGrantedAuthority("ROLE_PA"));
                        break;
                case "professor":  
                        authorities.add(new SimpleGrantedAuthority("ROLE_PROF"));
                        break;
                case "mitarbeiter":  
                        authorities.add(new SimpleGrantedAuthority("ROLE_MA"));
                        break;
                case "student":  
                        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
                        break;
            }
        }

        return new UserPrinciple(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(username, user.username);
    }
}