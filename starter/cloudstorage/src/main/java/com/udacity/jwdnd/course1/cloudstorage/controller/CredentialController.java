package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService){
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credentialsubmit")
    public String submitCredential(@ModelAttribute("credentialObject") Credential credential, Authentication authentication) {

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
            //credential.printall();
            credentialService.createCredential(credential);
        }
        return "redirect:/home";
    }

}
