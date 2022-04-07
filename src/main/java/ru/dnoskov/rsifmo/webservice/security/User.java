package ru.dnoskov.rsifmo.webservice.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.Setter;

public class User {

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	@Getter
	@Setter
    private String username;
	
	@Getter
    private String password;    

	@Getter
	@Setter
    private String[] roles;

    public User(String username, String password, String[] roles) {
        this.username = username;
        setPassword(password);
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

}
