package com.djenterprise.app.builder;

import com.djenterprise.db.connection.DBConnection;

public class ProjectBuilder {

    static public void main( String[] args ) {
        DBConnection.initialize();
    }
}