package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper=fileMapper;
    }

    public Integer addFile(File file){
        // returns the id if the file from filemapper
        System.out.println("FileService addFile called");
        return fileMapper.insert(file);
    }
    public List<File> getAllFilesForUid(Integer uid){
        return fileMapper.getAllFilesForUid(uid);
    }
}
