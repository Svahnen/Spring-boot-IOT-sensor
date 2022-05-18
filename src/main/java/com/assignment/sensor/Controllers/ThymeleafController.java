package com.assignment.sensor.Controllers;

import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

import com.assignment.sensor.DAO.Dao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {

    Dao dao = new Dao();

    @RequestMapping("/")
    public String index(Model model) throws SQLException {

        var c = new Dao.Cursor();
        model.addAttribute("temps", dao.getHistory(25, c));

        return "index";
    }

    @ExceptionHandler(SQLNonTransientConnectionException.class)
    public void handleSQLDisconnect() throws SQLException {
        dao.connect();
    }
}
