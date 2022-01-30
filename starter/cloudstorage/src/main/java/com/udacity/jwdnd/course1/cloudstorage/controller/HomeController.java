package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;

    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteObject") Note note, Model model) {
        //model.addAttribute("noteTitle", "hurz");
        //model.addAttribute("noteDescription", "Hurz ist von Hape Kerkeling");
        System.out.println("GET Title: " +  note.getNoteTitle());
        System.out.println("GET Description: " + note.getNoteDescription());
        //allNotes = noteService.getAllNotes();
        model.addAttribute("allNotes", noteService.getAllNotes());
        return "home";
    }

    @PostMapping("/home/formnote")
    public String postHomePage(@ModelAttribute("noteObject") Note note, Authentication authentication, Model model) {
        System.out.println("POST Title: " + note.getNoteTitle());
        System.out.println("POST Description: " + note.getNoteDescription());

        // retrieve the userId from the Database
        User currentUser = userService.getUser(authentication.getName());
        note.setUserId(currentUser.getUserId());
        noteService.createNote(note);
        return "home";
    }
}
