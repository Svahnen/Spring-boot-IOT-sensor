package com.assignment.sensor.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.assignment.sensor.Modules.Temp;

public class Dao {
    Connection con;

    public void connect() throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://100.107.10.54/data?user=grupp&password=iot");
    }

    public Temp getLatestTemp() throws SQLException {
        var query = "SELECT id, value, timestamp FROM temperature ORDER BY timestamp DESC LIMIT 1";
        var stmt = con.prepareStatement(query);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                var id = rs.getLong(1);
                var temp = rs.getFloat(2);
                var ts = rs.getTimestamp(3).toLocalDateTime();
                return new Temp(id, temp, ts);
            } else
                return null;
        }
    }
}