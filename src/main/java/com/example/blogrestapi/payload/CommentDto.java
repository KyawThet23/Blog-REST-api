package com.example.blogrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String body;
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    @NotEmpty(message = "comment must not be empty.")
    private String name;
}
