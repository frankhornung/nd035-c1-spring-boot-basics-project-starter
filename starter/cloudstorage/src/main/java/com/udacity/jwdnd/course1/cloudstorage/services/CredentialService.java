package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        // returns the id of created node as received from noteMapper
        System.out.println("CredentialService createCredential called");

        // encrypt before save
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);


        return credentialMapper.insert(credential);
    }

    public List<Credential> getAllCredentialsForUid(Integer uid){
        System.out.println("get all credentials for uid " + uid + " called");
        System.out.println("returning # elements: " + credentialMapper.getAllCredentialsForUid(uid).size());
        return credentialMapper.getAllCredentialsForUid(uid);
    }

    /*

    public void deleteNoteById(Integer noteId){
        System.out.println("delete note with id" + noteId.toString());
        noteMapper.deleteNoteById(noteId);
    }

    public void updateNote(Note note){
        System.out.println("update note with id: " + note.getNoteId().toString());
        Integer bla = noteMapper.updateNote(note);
        System.out.println("ret is: " + bla);
    }
*/
}
