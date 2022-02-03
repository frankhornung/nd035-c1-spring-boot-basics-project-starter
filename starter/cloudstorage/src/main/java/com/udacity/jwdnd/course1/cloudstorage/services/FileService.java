package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper=fileMapper;
    }

    public Integer addFile(File file){
        // returns the id if the file from filemapper
        System.out.println("NoteService createNote called");
        return fileMapper.insert(file);
    }
}
