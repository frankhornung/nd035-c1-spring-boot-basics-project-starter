package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/notesubmit")
    public String submitNote(@ModelAttribute("noteObject") Note note, Authentication authentication, Model model) {

        if (note.getNoteId() != null){
            System.out.println("noteId is: " + note.getNoteId().toString());
            if(note.getUserId() == null){
                User currentUser = userService.getUser(authentication.getName());
                note.setUserId(currentUser.getUserId());
            }
            noteService.updateNote(note);
        }
        else{
            System.out.println("noteId is NULL");
            // retrieve the userId from the Database
            User currentUser = userService.getUser(authentication.getName());
            note.setUserId(currentUser.getUserId());
            noteService.createNote(note);
        }
        //return "redirect:/home";
        model.addAttribute("success", "Success");
        return "result";
    }

    @RequestMapping("/notedelete")
    public String deleteNote(@RequestParam(value = "noteId", required = true) Integer noteId, Model model){
        System.out.println("notedelete with noteId param " + noteId);
        noteService.deleteNoteById(noteId);
        //return "redirect:/home";
        model.addAttribute("success", "Success");
        return "result";
    }

}
