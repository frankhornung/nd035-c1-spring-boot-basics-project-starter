package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (credentialId, url, userName, password, key, userid) VALUES(#{credentialId}, #{url}, #{userName}, #{password}, #{key}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{uid}")
    List<Credential> getAllCredentialsForUid(Integer uid);

    /*
    @Select("SELECT COUNT(*) FROM CREDENTIALS where userId = #{uid} and filename = #{filename}")
    int getCountOfCredential(String filename,Integer uid);
*/
    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userId = #{userId}")
    void deleteCredentialById(Integer credentialId, Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userId = #{userId}")
    Credential getCredentialById(Integer credentialId, Integer userId);

}
