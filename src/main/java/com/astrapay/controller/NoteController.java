package com.astrapay.controller;

import com.astrapay.dto.*;
import com.astrapay.exception.DataNotFoundException;
import com.astrapay.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Api(value = "Note Controller")
@RequestMapping("/v1/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;


    @GetMapping("/")
    @ApiOperation(value = "Get all notes")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = GetNotesResponseDto.class),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    public ResponseEntity<BaseResponseDto<GetNotesResponseDto>> getAll() {
        List<NoteDto> notes = this.noteService.getNotes();
        GetNotesResponseDto response = new GetNotesResponseDto();
        response.setNotes(notes);
        return BaseResponseDto.build(HttpStatus.OK, "Success", response);
    }

    @PostMapping("/")
    @ApiOperation(value = "Create a note")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = CreateUpdateDeleteNoteResponseDto.class),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    public ResponseEntity<BaseResponseDto<CreateUpdateDeleteNoteResponseDto>> create(@Valid @RequestBody CreateUpdateNoteRequestDto requestDto) {
        NoteDto note = this.noteService.create(requestDto);
        CreateUpdateDeleteNoteResponseDto response = new CreateUpdateDeleteNoteResponseDto();
        response.setNoteId(note.getId());
        return BaseResponseDto.build(HttpStatus.CREATED, "Success", response);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a note")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = CreateUpdateDeleteNoteResponseDto.class),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    public ResponseEntity<BaseResponseDto<CreateUpdateDeleteNoteResponseDto>> update(@PathVariable("id") String id, @Valid @RequestBody CreateUpdateNoteRequestDto requestDto) throws DataNotFoundException {

        NoteDto note = this.noteService.update(Long.valueOf(id), requestDto);
        CreateUpdateDeleteNoteResponseDto response = new CreateUpdateDeleteNoteResponseDto();
        response.setNoteId(note.getId());
        return BaseResponseDto.build(HttpStatus.OK, "Success", response);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a note")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = CreateUpdateDeleteNoteResponseDto.class),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    public ResponseEntity<BaseResponseDto<CreateUpdateDeleteNoteResponseDto>> delete(@PathVariable("id") String id) throws DataNotFoundException {
        this.noteService.delete(Long.valueOf(id));
        CreateUpdateDeleteNoteResponseDto response = new CreateUpdateDeleteNoteResponseDto();
        response.setNoteId(Long.valueOf(id));
        return BaseResponseDto.build(HttpStatus.OK, "Success", null);
    }
}
