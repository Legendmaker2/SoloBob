package com.codefair.solobob;

public class RegisterUserTO {

    private String email;
    private String password;
    private String name;

    public RegisterUserTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
