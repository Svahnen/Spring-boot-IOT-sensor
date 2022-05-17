package com.assignment.sensor.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public class Cursor {
        long id = 0;
    }

    public Cursor newCursor() {
        return new Cursor();
    }

    public ArrayList<Temp> getHistory(int howmany, Cursor cur) throws SQLException {
        ArrayList<Temp> res = new ArrayList<Temp>();
        var sqlFirst = "SELECT id, value, timestamp FROM temperature ORDER BY id DESC LIMIT ?";
        var sqlRest = "SELECT id, value, timestamp FROM temperature WHERE id < ? ORDER BY id DESC LIMIT ?";

        PreparedStatement stmt;
        ResultSet rs = null;
        long last_id = 0;
        try {
            if (cur.id == 0) {
                stmt = con.prepareStatement(sqlFirst);
                stmt.setInt(1, howmany);
                rs = stmt.executeQuery();
            } else {
                stmt = con.prepareStatement(sqlRest);
                stmt.setInt(2, howmany);
                stmt.setLong(1, cur.id);
                rs = stmt.executeQuery();
            }
            while (rs.next()) {
                var temp = rs.getFloat("value");
                var id = rs.getLong("id");
                var timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                res.add(new Temp(id, temp, timestamp));
                last_id = id;
            }
        } finally {
            rs.close();
        }
        cur.id = last_id;
        return res;
    }
}