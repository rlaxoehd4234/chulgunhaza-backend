package com.example.chulgunhazabackend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="posts")
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Embedded
    private Category category;


    @ElementCollection
    @CollectionTable(name = "postImages", joinColumns = @JoinColumn(name = "post_id"))
    private List<PostImage> postImagesList = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "postFiles", joinColumns = @JoinColumn(name = "post_id"))
    private List<PostImage> postFilesList = new ArrayList<>();


    @ColumnDefault("0")
    private int count;
}
