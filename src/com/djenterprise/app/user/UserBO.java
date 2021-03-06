package com.djenterprise.app.user;

import com.djenterprise.app.authentication.AESenc;

import java.io.InputStream;
import java.sql.Blob;

public class UserBO {

    private String username;
    private String password;
    private String alias;
    private Blob avatar;
    private InputStream inputStream;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    /**
     * Get encrypted password of the user.
     * @return encrypted password by AES standard.
     */
    public String encryptedPassword() throws Exception {
        return AESenc.encrypt(password);
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}