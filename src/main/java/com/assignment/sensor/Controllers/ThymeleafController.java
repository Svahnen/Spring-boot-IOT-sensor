package com.assignment.sensor.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.assignment.sensor.Modules.Temp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {

    List<Temp> temps = new ArrayList<>();

    @RequestMapping("/")
    public String index(Model model) {
        temps.clear();
        temps.add(new Temp(1, 20, LocalDateTime.now()));
        temps.add(new Temp(2, 21, LocalDateTime.now()));
        temps.add(new Temp(3, 22, LocalDateTime.now()));
        temps.add(new Temp(4, 23, LocalDateTime.now()));
        temps.add(new Temp(5, 24, LocalDateTime.now()));
        temps.add(new Temp(6, 25, LocalDateTime.now()));
        temps.add(new Temp(7, 26, LocalDateTime.now()));
        temps.add(new Temp(8, 27, LocalDateTime.now()));
        temps.add(new Temp(9, 28, LocalDateTime.now()));
        temps.add(new Temp(10, 29, LocalDateTime.now()));
        temps.add(new Temp(11, 30, LocalDateTime.now()));

        model.addAttribute("temps", temps);

        return "index";
    }
}
