package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication){

        System.out.println("/file-upload Post mapping called");
        User currentUser = userService.getUser(authentication.getName());

        File file = null;
        try {
            file = new File(fileUpload.getOriginalFilename(), fileUpload.getContentType(), fileUpload.getSize() + "", currentUser.getUserId(), fileUpload.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileService.addFile(file);
        model.addAttribute("success", "Success");
        return "result";
    }

}
