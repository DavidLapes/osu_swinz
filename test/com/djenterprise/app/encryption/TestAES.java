package com.djenterprise.app.encryption;

import com.djenterprise.app.user.AESenc;
import org.junit.Assert;
import org.junit.Test;

public class TestAES {

    static final private String PASSWORD = "1234";

    @Test
    public void test01AESEncrypt() throws Exception {
        System.out.println(AESenc.encrypt(PASSWORD));
    }

    @Test
    public void test02AESDecrypt() throws Exception {
        String crypt = AESenc.encrypt(PASSWORD);
        System.out.println(AESenc.decrypt(crypt));
    }

    @Test
    public void test03AESEncryptionDecryptionMatch() throws Exception {
        String actual = AESenc.encrypt(PASSWORD);
        actual = AESenc.decrypt(actual);
        Assert.assertEquals(PASSWORD, actual);
    }
}