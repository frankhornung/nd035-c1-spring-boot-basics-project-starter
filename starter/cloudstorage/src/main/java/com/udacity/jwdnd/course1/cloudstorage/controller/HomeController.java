package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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

    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteObject") Note note, Model model) {
        System.out.println("GET /home");
        model.addAttribute("allNotes", noteService.getAllNotes());
        return "home";
    }


}
