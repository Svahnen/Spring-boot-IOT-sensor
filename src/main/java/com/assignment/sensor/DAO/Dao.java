package com.assignment.sensor.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {
    Connection con;

    public void connect() throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb:100.107.10.54/data?user=grupp&password=iot");
    }

    public void getLatestTemp() throws SQLException {
        var query = "SELECT id, value, timestamp FROM temperature ORDER BY timestamp DESC LIMIT 1";
        Statement stmt = con.prepareStatement(query);
        try (ResultSet)
    }
}