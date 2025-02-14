package com.astrapay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.astrapay.dto.CreateUpdateNoteRequestDto;
import com.astrapay.dto.NoteDto;
import com.astrapay.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testGetAll() throws Exception {
        List<NoteDto> notes = new ArrayList<>();
        notes.add(new NoteDto(0L, "title", "content"));
        notes.add(new NoteDto(1L, "title1", "content1"));
        Mockito.when(noteService.getNotes()).thenReturn(notes);

        mockMvc.perform(get("/v1/note/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.notes[0].id").value(0))
                .andExpect(jsonPath("$.data.notes[0].title").value("title"))
                .andExpect(jsonPath("$.data.notes[0].content").value("content"))
                .andExpect(jsonPath("$.data.notes[1].id").value(1))
                .andExpect(jsonPath("$.data.notes[1].title").value("title1"))
                .andExpect(jsonPath("$.data.notes[1].content").value("content1"));
    }

    @Test
    public void testCreate() throws Exception {
        CreateUpdateNoteRequestDto requestDto = new CreateUpdateNoteRequestDto();
        requestDto.setTitle("title");
        requestDto.setContent("content");
        NoteDto note = new NoteDto(0L, "title", "content");
        Mockito.when(noteService.create(requestDto)).thenReturn(note);

        mockMvc.perform(post("/v1/note/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.noteId").value(0));
    }

    @Test
    public void testUpdate() throws Exception {
        CreateUpdateNoteRequestDto requestDto = new CreateUpdateNoteRequestDto();
        requestDto.setTitle("title Update");
        requestDto.setContent("content Update");
        Long id = 0L;
        NoteDto note = new NoteDto(0L, "title", "content");
        Mockito.when(noteService.update(id, requestDto)).thenReturn(note);

        mockMvc.perform(put("/v1/note/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.noteId").value(0));
    }

    @Test
    public void testDelete() throws Exception {

        Long id = 0L;
        Mockito.doNothing().when(noteService).delete(id);

        mockMvc.perform(delete("/v1/note/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));

    }
}
