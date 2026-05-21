package com.CheatSheet.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginBean {
	private int id;
    private String username;
    private String email;
    private String password;
    private int enabled;
    private String roles;
}
