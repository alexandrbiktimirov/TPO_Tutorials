package org.example.tutorial9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/BMI")
    @ResponseBody
    public String BMI(@RequestParam double weight, @RequestParam double height, @RequestParam(defaultValue = "json") String format) {
        return null;
    }

    @GetMapping("/BMR/{gender}")
    @ResponseBody
    public String BMR(@PathVariable String gender, @RequestParam double weight, @RequestParam double height, @RequestParam int age, @RequestParam(defaultValue = "json") String format) {
        return null;
    }
}
