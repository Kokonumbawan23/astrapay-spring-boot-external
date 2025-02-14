package com.astrapay.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetNotesResponseDto {
    List<NoteDto> notes;
}
