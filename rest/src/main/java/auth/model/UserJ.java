package auth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserJ {

    private String authenticatedUser;
    private String groups;
    private String tenants;
    private boolean authenticated;

    public UserJ() {
    }

    public String getUsername() {
        return authenticatedUser;
    }

    public void setType(String username) {
        this.authenticatedUser = username;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean auth) {
        this.authenticated = auth;
    }

    @Override
    public String toString() {
        return "User{" +
                "Username='" + authenticatedUser + '\'' +
                ", is authenticated=" + authenticated +
                ", as "+ groups +" }";
    }
}