package com.example.chulgunhazabackend.service.impl;

import com.example.chulgunhazabackend.domain.board.Category;
import com.example.chulgunhazabackend.domain.board.Post;
import com.example.chulgunhazabackend.dto.PageDto;
import com.example.chulgunhazabackend.dto.board.*;
import com.example.chulgunhazabackend.exception.postException.PostException;
import com.example.chulgunhazabackend.exception.postException.PostExceptionType;
import com.example.chulgunhazabackend.repository.PostRepository;
import com.example.chulgunhazabackend.service.FileService;
import com.example.chulgunhazabackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final FileService fileService;

//    TODO: userService 추가 후 추가 예정
//    @Autowired
//    private UserService userService

    @Transactional(rollbackFor = IOException.class)
    public Long create(PostCreateRequestDto dto, List<MultipartFile> postFiles) throws IOException {
        return postRepository.save(dto.toEntity(new Category(dto.getCategoryName()), fileService.savePostFiles(postFiles))).getId();
    }

    @Transactional(readOnly = true)
    public PostSearchResponseDto findById(Long postNumber) throws MalformedURLException {
        Post post = validAfterGetPost(postNumber);
        // Redis 추가 시 count 증가 로직.. 추가
        return new PostSearchResponseDto().fromEntity(post, fileService.findPostFiles(post.getPostFilesList()));
    }

    public Long deleteById(Long postNumber) throws MalformedURLException {
        Post post = validAfterGetPost(postNumber);
        post.delete();
        return postRepository.save(post).getId();
    }

    @Transactional(rollbackFor = IOException.class)
    public Long modifyById(Long postNumber, PostModifyRequestDto dto, List<MultipartFile> postFiles) throws IOException {
        Post post = validAfterGetPost(postNumber);
        post.updatePost(dto.getTitle(), dto.getContent(), new Category(dto.getCategoryName()), fileService.savePostFiles(postFiles));
        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public PageDto<PostListResponseDto> findAllByDelFlagFalseAndCategory(Pageable pageable, String category){
        Page<PostListResponseDto> contents = postRepository.findAllByDelFlagFalseAndCategory(pageable, new Category(category)).map(post -> new PostListResponseDto().fromEntity(post));
        return new PageDto<PostListResponseDto>(contents);
    }

    private Post validAfterGetPost(Long postNumber){
        return postRepository.findByIdAndDelFlagFalse(postNumber).orElseThrow(() ->new PostException(PostExceptionType.POST_NOT_FOUND));
    }
}
