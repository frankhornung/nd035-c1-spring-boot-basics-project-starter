package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// inspired by the mentor's links
// https://www.baeldung.com/spring-boot-custom-error-page
// https://stackoverflow.com/questions/25356781/spring-boot-remove-whitelabel-error-page
// https://www.websparrow.org/spring/how-to-resolve-whitelabel-error-page-in-spring-boot
// https://www.javadevjournal.com/spring-boot/spring-boot-whitelabel-error-page/
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String customErrors(Model model){
        model.addAttribute("errorMsg", "oopsie");
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
