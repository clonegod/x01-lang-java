package com.asynclife.basic.advanced.exception;

public class SigninService {

    public void signin(final String username, final String password) {
        if (!exists(username, password)) {
            throw new NotAuthenticatedException(
                    "User / Password combination is not recognized");
        }
    }

    private boolean exists(String username, String password) {
        return false;
    }
}
