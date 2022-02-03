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
    public Boolean filenameFree(String filename, Integer uid){
        System.out.println("FileService isFilenameOfUserUnique called");
        Integer count = fileMapper.getCountOfFile(filename,uid);
        if(count == 0){
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }

    public void deleteFileById(Integer fileId){
        System.out.println("delete file with id" + fileId.toString());
        fileMapper.deleteFileById(fileId);
    }

}
