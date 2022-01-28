package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/home")
public class HomeController {

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("noteTitle", "hurz");
        model.addAttribute("noteDescription", "Hurz ist von Hape Kerkeling");
        return "home";
    }

    @PostMapping("/home")
    public String postHomePage(Model model) {
        //model.addAttribute("noteTitle", "hurz");
        //model.addAttribute("noteDescription", "Hurz ist von Hape Kerkeling");
        System.out.println("Title: " + model.getAttribute("noteTitle"));
        System.out.println("Title: " + model.getAttribute("noteDescription"));

        return "home";
    }
}
