package com.example.chulgunhazabackend.repository;

import com.example.chulgunhazabackend.domain.board.Category;
import com.example.chulgunhazabackend.domain.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "postFilesList")
    Optional<Post> findByIdAndDelFlagFalse(Long postId);

    Page<Post> findAllByDelFlagFalseAndCategory(Pageable pageable, Category category);

}
