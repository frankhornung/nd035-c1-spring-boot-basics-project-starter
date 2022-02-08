package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService){
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credentialsubmit")
    public String submitCredential(@ModelAttribute("credentialObject") Credential credential, Authentication authentication, Model model) {

        User currentUser = userService.getUser(authentication.getName());

        if (credential.getCredentialId() != null){
            System.out.println("credentailId is: " + credential.getCredentialId().toString());
            if(credential.getUserId() == null){
                credential.setUserId(currentUser.getUserId());
            }
            credentialService.updateCredential(credential);
        }
        else{
            System.out.println("credentialId is NULL");
            credential.setUserId(currentUser.getUserId());
            credentialService.createCredential(credential);
        }
//        return "redirect:/home";
        model.addAttribute("success", "Success");
        return "result";
    }

    @RequestMapping("/credentialdelete")
    public String deleteCredential(@RequestParam(value = "credentialId", required = true) Integer credentialId, Authentication authentication, Model model){
        User currentUser = userService.getUser(authentication.getName());
        System.out.println("credentialdelete with noteId param " + credentialId);
        credentialService.deleteCredentialById(credentialId, currentUser.getUserId());
        //return "redirect:/home";
        model.addAttribute("success", "Success");
        return "result";
    }
}
