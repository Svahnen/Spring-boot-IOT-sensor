package com.assignment.sensor.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import com.assignment.sensor.DAO.Dao;
import com.assignment.sensor.Modules.Temp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TestController {
    Dao dao;

    public TestController() throws SQLException {
        dao = new Dao();
        dao.connect();
    }

    @RequestMapping("/test") // http://localhost:8080/test
    public Temp index() throws SQLException {
        return dao.getLatestTemp();
    }

    @RequestMapping("/test2") // http://localhost:8080/test2
    public ArrayList<Temp> index2(
            @RequestParam(name = "cursor", required = false, defaultValue = "0") long cur)
            throws SQLException {
        var c = new Dao.Cursor(cur);
        return dao.getHistory(50, c);
    }

    @RequestMapping("/test3") // http://localhost:8080/test3
    public ArrayList<Temp> index3(
            @RequestParam(name = "cursor", required = false, defaultValue = "0") long cur)
            throws SQLException {
        var c = new Dao.Cursor();
        c.newest = cur;
        return dao.getNewer(c);
    }
}