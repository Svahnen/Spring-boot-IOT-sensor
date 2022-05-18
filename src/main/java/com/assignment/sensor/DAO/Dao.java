package com.assignment.sensor.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.assignment.sensor.Modules.Temp;

public class Dao {
    Connection con;

    public Dao() {
        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                var ts = rs.getString(3);
                return new Temp(id, temp, ts);
            } else
                return null;
        }
    }

    public static class Cursor {
        public static long NOT_STARTED_VAL = 0;
        long oldest = 0;
        public long newest = 0;

        public Cursor() {
            oldest = 0;
            newest = 0;
        }

        public Cursor(long start) {
            oldest = start;
            newest = 0;
        }

        public long toNumber() {
            return oldest;
        }
    }

    public Cursor newCursor() {
        return new Cursor();
    }

    public ArrayList<Temp> getAllHistory() throws SQLException {
        ArrayList<Temp> res = new ArrayList<Temp>();
        var sql = "SELECT id, value, timestamp FROM temperature ORDER BY id ASC";
        var stmt = con.prepareStatement(sql);
        try (var rs = stmt.executeQuery()) {
            while (rs.next()) {
                var temp = rs.getFloat("value");
                var id = rs.getLong("id");
                var timestamp = rs.getString("timestamp");
                res.add(new Temp(id, temp, timestamp));
            }
        }
        return res;
    }

    public ArrayList<Temp> getHistory(int howmany, Cursor cur) throws SQLException {
        ArrayList<Temp> res = new ArrayList<Temp>();
        var sqlFirst = "SELECT id, value, timestamp FROM temperature ORDER BY id DESC LIMIT ?";
        var sqlRest = "SELECT id, value, timestamp FROM temperature WHERE id < ? ORDER BY id DESC LIMIT ?";

        PreparedStatement stmt;
        ResultSet rs = null;
        try {
            if (cur.oldest == 0) {
                stmt = con.prepareStatement(sqlFirst);
                stmt.setInt(1, howmany);
                rs = stmt.executeQuery();
            } else {
                stmt = con.prepareStatement(sqlRest);
                stmt.setInt(2, howmany);
                stmt.setLong(1, cur.oldest);
                rs = stmt.executeQuery();
            }
            while (rs.next()) {
                var temp = rs.getFloat("value");
                var id = rs.getLong("id");
                var timestamp = rs.getString("timestamp");
                if (cur.newest < id) {
                    cur.newest = id;
                }
                if (cur.oldest > id) {
                    cur.oldest = id;
                }
                res.add(new Temp(id, temp, timestamp));
            }
        } finally {
            rs.close();
        }
        Collections.reverse(res);
        return res;
    }

    public ArrayList<Temp> getNewer(Cursor cur) throws SQLException {
        ArrayList<Temp> res = new ArrayList<Temp>();

        var sql = "SELECT id, value, timestamp FROM temperature WHERE id > ? ORDER BY id ASC";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setLong(1, cur.newest);
        long last_id = 0;

        try (var rs = stmt.executeQuery()) {
            while (rs.next()) {
                var temp = rs.getFloat("value");
                var id = rs.getLong("id");
                var timestamp = rs.getString("timestamp");
                res.add(new Temp(id, temp, timestamp));
                last_id = id;
            }
        }

        cur.newest = last_id;
        return res;
    }
}