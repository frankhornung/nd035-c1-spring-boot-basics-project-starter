package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView(Model model, HttpServletRequest request) {

        // inspiration for implementing it this way from https://www.baeldung.com/spring-web-flash-attributes
        // the inputFlashMap is a HashMap managed by Spring in order to implement a PRG (post, redirect, get) pattern
        // using this functionality i can transfer the signupSuccess and logoutSuccess events to the login page
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            if(inputFlashMap.containsKey("signupSuccess")) {
                Boolean signupSuccess = (Boolean) inputFlashMap.get("signupSuccess");
                if (signupSuccess) {
                    model.addAttribute("signupSuccess", true);
                }
            }
            if(inputFlashMap.containsKey("logoutSuccess")){
                Boolean logoutSuccess = (Boolean) inputFlashMap.get("logoutSuccess");
                if(logoutSuccess){
                    model.addAttribute("logoutSuccess", true);
                }
            }
        }

        return "login";
    }
}
