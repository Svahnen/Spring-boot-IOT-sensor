package com.assignment.sensor.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
    Connection con;

    public void connect() throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb:localhost/data?user=grupp&password=iot");
    }
}