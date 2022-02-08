package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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

        System.out.println("DBG filename:" + file.getFilename());
        if(!file.getFilename().isEmpty() && file.getFilename() != null){
            if (fileService.filenameFree(fileUpload.getOriginalFilename(), currentUser.getUserId())){
                fileService.addFile(file);
                model.addAttribute("success", "Success");
            }
            else{
                model.addAttribute("failed", "File does already exist, you cannot upload it again");
            }
        }
        else{
            model.addAttribute("failed", "Filename is empty");
        }
        return "result";
    }

    @RequestMapping("/filedelete")
    public String deleteFile(@RequestParam(value = "fileId", required = true) Integer fileId, Model model, Authentication authentication){
        System.out.println("filedelete with fileId param " + fileId);

        User currentUser = userService.getUser(authentication.getName());
        File file = fileService.getFileById(fileId, currentUser.getUserId());

        fileService.deleteFileById(fileId, currentUser.getUserId());
        //return "redirect:/home";
        model.addAttribute("success", "Success");
        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(value = "fileId", required = true) Integer fileId, Authentication authentication){
        System.out.println("/download post mapping called");

        User currentUser = userService.getUser(authentication.getName());
        File file = fileService.getFileById(fileId, currentUser.getUserId());

        HttpHeaders headers = new HttpHeaders();

        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ContentDisposition.html
        ContentDisposition disposition;
        disposition = ContentDisposition.builder("attachment").name("foo").filename(file.getFilename()).build();
        headers.setContentDisposition(disposition);

        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/MediaType.html
        headers.setContentType(MediaType.parseMediaType(file.getContenttype()));

        // https://stackoverflow.com/questions/386845/http-headers-for-file-downloads
        // https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(file.getFiledata()));
                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ByteArrayResource.html
    }

}
