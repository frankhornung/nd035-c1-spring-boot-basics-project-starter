package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("credentialObject") Credential credential, @ModelAttribute("noteObject") Note note, Model model, Authentication authentication) {
        System.out.println("GET /home");
        User currentUser = userService.getUser(authentication.getName());
        Integer uid = currentUser.getUserId();
        model.addAttribute("allNotes", noteService.getAllNotesForUid(uid));
        model.addAttribute("allFiles",fileService.getAllFilesForUid(uid));
        model.addAttribute("allCredentials", credentialService.getAllCredentialsForUid(uid));
        return "home";
    }


}
