package com.CheatSheet.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterLoginBean {
    private int id;
    private String username;
    private String email;
    private String password;
    private int enabled;
    private String roleName; //

    // Getter for roleName
    public String getRoleName() {
        return roleName;
    }

    // Setter for roleName
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // ... existing getters and setters for other fields ...
}