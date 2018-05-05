package com.djenterprise.db.dao;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.user.Login;
import com.djenterprise.db.user.UserDAO;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUserDAO {

    static final private Logger LOGGER = Logger.getLogger(TestUserDAO.class.getName());

    @Test
    public void test01GetUserUsername(){
        UserBO expected = new UserBO();
        expected.setUsername("David");
        UserBO actual = UserDAO.getUser("David");
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    public void test02GetUserAlias(){
        UserBO expected = new UserBO();
        expected.setAlias("David");
        UserBO actual = UserDAO.getUser("David");
        Assert.assertEquals(expected.getAlias(), actual.getAlias());
    }
    @Test
    public void test03CreateUser(){
        UserBO user = new UserBO();
        user.setUsername("Test");
        user.setPassword("test");
        user.setAlias("TesT");
        UserBO expected = new UserBO();
        expected.setAlias("TesT");
        UserDAO.createUser(user);
        UserBO actual = UserDAO.getUser("Test");
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    public void test04editUserAlias (){
        UserBO user = new UserBO();
        user.setUsername("TestAlias01");
        user.setPassword("Test");
        user.setAlias("Test01");
        UserDAO.createUser(user);
        user.setAlias("TestAlias");
        UserBO expected = new UserBO();
        expected.setAlias("TestAlias");
        UserDAO.editUserAlias(user);
        UserBO actual = UserDAO.getUser("TestAlias01");
        Assert.assertEquals(expected.getAlias(), actual.getAlias());
    }

    @Test
    public void test05editUserPassword (){
        UserBO user = new UserBO();
        user.setUsername("TestPassword01");
        user.setPassword("TestPassword01");
        user.setAlias("Test01");
        UserDAO.createUser(user);
        user.setPassword("TestPassword02");
        UserDAO.editUserPassword(user);
        try {
            Assert.assertEquals(true, Login.testLogin(user));
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
