package com.djenterprise.app.encryption;

import com.djenterprise.app.authentication.Registration;
import org.junit.Assert;
import org.junit.Test;

public class TestAuthentication {

    static final private String CORRECT_ALIAS = "951David_Frýdecký753";
    static final private String WRONG_ALIAS = "David Frýdecký";
    static final private String CORRECT_USERNAME = "456mrzynn159";
    static final private String WRONG_USERNAME = "752mr zynn852";
    static final private String CORRECT_PASSWORD = "MisterZynnarion123";
    static final private String WRONG_PASSWORD = "Mister Zynnarion 123";

    @Test
    public void test01correctUsername() {
        Assert.assertTrue(
                Registration.checkUsername(CORRECT_USERNAME)
        );
    }

    @Test
    public void test02wrongUsername() {
        Assert.assertTrue(
                ! Registration.checkUsername(WRONG_USERNAME)
        );
    }

    @Test
    public void test03correctAlias() {
        Assert.assertTrue(
                Registration.checkAlias(CORRECT_ALIAS)
        );
    }

    @Test
    public void test04wrongAlias() {
        Assert.assertTrue(
                ! Registration.checkAlias(WRONG_ALIAS)
        );
    }

    @Test
    public void test05correctPassword() {
        Assert.assertTrue(
                Registration.checkPassword(CORRECT_PASSWORD)
        );
    }

    @Test
    public void test06wrongPassword() {
        Assert.assertTrue(
                ! Registration.checkPassword(WRONG_PASSWORD)
        );
    }
}