package com.astrapay.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUpdateNoteRequestDto {
    @NotNull(message = "Title is mandatory!")
    private String title;

    @NotNull(message = "Content is mandatory!")
    private String content;
}
