package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) VALUES(#{fileId}, #{filename}, #{contenttype}, #{filesize}, #{userId}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE userId = #{uid}")
    List<File> getAllFilesForUid(Integer uid);

    @Select("SELECT COUNT(*) FROM FILES where userId = #{uid} and filename = #{filename}")
    int getCountOfFile(String filename,Integer uid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    void deleteFileById(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    File getFileById(Integer fileId, Integer userId);

}
