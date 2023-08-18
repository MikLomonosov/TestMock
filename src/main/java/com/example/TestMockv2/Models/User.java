package com.example.TestMockv2.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {

    //region Properties
    private String login;
    private String password;
    private String email;
    private Date date;
    //endregion

    //region Constructors
    public User() {}

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
    //endregion


    //region Methods
    @Override
    public String toString() {
        return "{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean IsValid() {
        return login != null &&
                password != null &&
                email != null &&
                date != null;
    }
    //endregion
}
