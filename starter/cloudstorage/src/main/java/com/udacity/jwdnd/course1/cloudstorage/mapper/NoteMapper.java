package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteid}")
    Note getNoteById(Integer noteId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteid}")
    void deleteNoteById(Integer noteId);

    @Insert("INSERT INTO NOTES (noteId, noteTitle, noteDescription, userId) VALUES(#{noteId}, #{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes();

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription}, userId = #{userId} WHERE noteId = #{noteId}")
    void updateNote(Note note);
}
