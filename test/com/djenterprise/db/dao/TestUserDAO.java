package com.djenterprise.db.dao;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.user.UserDAO;
import org.junit.Assert;
import org.junit.Test;

public class TestUserDAO {

    @Test
    public void test01GetUserUsername(){
        UserBO expected = new UserBO();
        expected.setUsername("David");
        UserBO actual = UserDAO.getUser("David");
        Assert.assertEquals(actual.getUsername(), expected.getUsername());
    }

    @Test
    public void test02GetUserAlias(){
        UserBO expected = new UserBO();
        expected.setAlias("David");
        UserBO actual = UserDAO.getUser("David");
        Assert.assertEquals(actual.getAlias(), expected.getAlias());
    }

}
