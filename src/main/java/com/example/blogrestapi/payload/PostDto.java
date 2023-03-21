package com.example.blogrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "Information for post entity."
)
public class PostDto {
    private Long id;
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotBlank(message = "Description must not be blank")
    private String description;
    @NotBlank(message = "Content must not be blank")
    private String content;
    private Set<CommentDto> comments;
    private Long catId;
}
