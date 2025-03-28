package com.example.chulgunhazabackend.dto.board;

import com.example.chulgunhazabackend.domain.board.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private String title;
    //    private String author;
    private int count;
    private LocalDateTime createdAt;


    public PostListResponseDto fromEntity(Post post){
        return new PostListResponseDto(
                post.getTitle()
                ,post.getCount()
                ,post.getCreatedAt()
        );
    }
}
