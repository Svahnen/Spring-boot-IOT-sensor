package com.assignment.sensor.Controllers;

import java.sql.SQLException;

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
}