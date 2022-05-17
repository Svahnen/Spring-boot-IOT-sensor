package com.assignment.sensor.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import com.assignment.sensor.DAO.Dao;
import com.assignment.sensor.Modules.Temp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ArrayList<Temp> index2() throws SQLException {
        var c = dao.newCursor();
        return dao.getHistory(50, c);
    }
}