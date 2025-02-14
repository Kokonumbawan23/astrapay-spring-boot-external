package com.astrapay.service;

import com.astrapay.dto.CreateUpdateNoteRequestDto;
import com.astrapay.dto.NoteDto;
import com.astrapay.exception.DataNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private ModelMapper modelMapper;
    private final List<NoteDto> notesData = new ArrayList<>();
    private Long idSequence = 0L;

    /**
     * This method is intended to get all notes present in memory database.
     * @return all notes in database
     */
    public List<NoteDto> getNotes(){
        return this.notesData;
    }

    /**
     * This method is intended for creation of note. It will use
     * ModelMapper to map request into Dto. ID population in the note
     * is based on idSequence that would reset everytime application is
     * restarted
     * @param requestDto Request object that contain title and content of note
     * @return id of note that has been created
     * @throws DataNotFoundException if note is not found
     */
    public NoteDto create(CreateUpdateNoteRequestDto requestDto){
        NoteDto noteDto = modelMapper.map(requestDto, NoteDto.class);
        noteDto.setId(idSequence);

        this.notesData.add(noteDto);
        this.idSequence++;
        return noteDto;
    }

    /**
     * This method is intended to update note based on noteId. It will use
     * ModelMapper to map request into Dto. If note is not found, it will throw
     * DataNotFoundException
     * @param noteId Id of the note targeted for update
     * @param requestDto Request object that contain title and content of note
     * @return id of note that has been updated
     * @throws DataNotFoundException if note is not found
     */

    public NoteDto update(Long noteId, CreateUpdateNoteRequestDto requestDto) throws DataNotFoundException {

        Long searchedNoteId = null;
        for (NoteDto noteDto : this.notesData) {
            if (noteDto.getId().equals(noteId)) {
                searchedNoteId = noteId;
                break;
            }
        }
        System.out.println("Note ID: " + searchedNoteId);

        if(searchedNoteId == null){
            throw new DataNotFoundException("Note is not found");
        }

        NoteDto noteDto = modelMapper.map(requestDto, NoteDto.class);
        noteDto.setId(noteId);

        this.notesData.remove(Math.toIntExact(noteId));
        this.notesData.add(Math.toIntExact(noteId),noteDto);
        return noteDto;
    }

    /**
     * This method is intended to delete note based on noteId. It will remove the note
     * from notesData List
     * @param noteId Id of the note targeted for delete
     * @return null
     * @throws DataNotFoundException when note is not found
     */

    public Void delete(Long noteId) throws DataNotFoundException{
        Long searchedNoteId = null;
        for (NoteDto noteDto : this.notesData) {
            if (noteDto.getId().equals(noteId)) {
                searchedNoteId = noteId;
                break;
            }
        }
        if (searchedNoteId == null) {
            throw new DataNotFoundException("Note is not found");
        }
        this.notesData.remove(Math.toIntExact(searchedNoteId));
        return null;
    }
}
