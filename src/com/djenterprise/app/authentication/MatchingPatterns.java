package com.djenterprise.app.authentication;

public class MatchingPatterns {

    static final public String USERNAME_REGEX = "^[a-zA-Z0-9]$";
    static final public String ALIAS_REGEX = "\\A[\\pL|[0-9]]{1,32}\\z";
    static final public String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}$";
}