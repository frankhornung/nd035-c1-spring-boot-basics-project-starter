package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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

        // add the userId to the note pojo in case missing
        if(note.getUserId() == null){
            User currentUser = userService.getUser(authentication.getName());
            note.setUserId(currentUser.getUserId());
        }

        // update existing note
        if (note.getNoteId() != null){
            System.out.println("noteId is: " + note.getNoteId().toString());
            try{
                noteService.updateNote(note);
                model.addAttribute("success", "Success");
            }catch(DataIntegrityViolationException e){
                model.addAttribute("failed", "saving your note had issues. Your note is unchanged! Hint: the description you put may not exceed 1000 characters!");
                e.printStackTrace();
            }
        }
        // new note, needs creation
        else{
            System.out.println("noteId is NULL");
            try{
                noteService.createNote(note);
                model.addAttribute("success", "Success");
            }catch(DataIntegrityViolationException e){
                model.addAttribute("failed", "saving your note had issues. Hint: the description you put may not exceed 1000 characters!");
                e.printStackTrace();
            }
        }
        return "result";
    }

    @RequestMapping("/notedelete")
    public String deleteNote(@RequestParam(value = "noteId", required = true) Integer noteId, Model model){
        System.out.println("notedelete with noteId param " + noteId);

        noteService.deleteNoteById(noteId);

        model.addAttribute("success", "Success");
        return "result";
    }

}
