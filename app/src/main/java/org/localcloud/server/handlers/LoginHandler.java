package org.localcloud.server.handlers;

import org.localcloud.IAM.IAMHolder;
import org.localcloud.db.UserDB;
import org.localcloud.exceptions.AuthenticationException;

import java.sql.SQLException;

public class LoginHandler {
    private String username;
    private String password;

    public LoginHandler() {}

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public IAMHolder authenticateUser() throws IllegalArgumentException, SQLException, AuthenticationException{
        validationBreakPoint();
        return UserDB.authenticateUser(username, password);
    }
    public void validationBreakPoint() {
        if(username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new IllegalArgumentException("Invalid input format");
    }
}
