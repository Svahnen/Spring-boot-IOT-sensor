package com.assignment.sensor.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("temp", 22);
        return "index";
    }
}
