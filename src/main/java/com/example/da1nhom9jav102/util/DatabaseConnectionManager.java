package com.example.da1nhom9jav102.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private final String url;
    public  DatabaseConnectionManager(String databaseName,String userName,String password) {
        this.url = "jdbc:sqlsever://localhost:1433;database="+databaseName
        +";user="+userName
        +";password="+password
        +";encrypt=true;" + "trustSeverCertificate=true;" + "loginTimeOut=30;";
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
