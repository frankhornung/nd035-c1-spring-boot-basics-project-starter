package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
/*
    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }
*/
    public int createNote(Note note) {
        // returns the id of created node as received from noteMapper
        System.out.println("NoteService createNote called");
        return noteMapper.insert(note);
    }

    public List<Note> getAllNotes(){
        System.out.println("get all notes called");
        return noteMapper.getAllNotes();
    }

    public void deleteNoteById(Integer noteId){
        System.out.println("delete note with id" + noteId.toString());
        noteMapper.deleteNoteById(noteId);
    }

    public void updateNote(Note note){
        System.out.println("update note with id" + note.getNoteId().toString());
        noteMapper.updateNote(note);
    }

}
