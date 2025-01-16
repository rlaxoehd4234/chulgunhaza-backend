package com.example.chulgunhazabackend.dto;

import com.example.chulgunhazabackend.domain.board.Category;
import com.example.chulgunhazabackend.domain.board.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PostSearchResponseDto {
    private String title;
    private String content;
//    private String author;
    private List<String> imageList;
    private int count;
    private Category category;

    public PostSearchResponseDto fromEntity(Post post, List<String> imageList){
        return new PostSearchResponseDto(
                post.getTitle()
                ,post.getContent()
                ,imageList
                ,post.getCount()
                ,post.getCategory()
        );
    }


}
