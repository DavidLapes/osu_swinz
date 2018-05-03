package com.djenterprise.app.builder;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.user.UserDAO;
import com.djenterprise.web.user.DisplayAvatarServlet;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectBuilder {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ProjectBuilder.class.getName());

    static public void main( String[] args ) throws Exception {
        DBConnection.initialize();

        File file = new File("web/images/man.png");
        Path path = file.toPath().toAbsolutePath();

        UserBO user = new UserBO();
        user.setAlias("root_alias");
        user.setUsername("root_username");
        user.setPassword("r007_//_84ssw036d");
        user.setInputStream(new FileInputStream(path.toFile()));
        UserDAO.createUser(user);
    }
}