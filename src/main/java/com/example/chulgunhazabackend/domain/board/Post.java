package com.example.chulgunhazabackend.domain.board;

import com.example.chulgunhazabackend.domain.common.BaseEntity;
import com.example.chulgunhazabackend.domain.member.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="posts")
@ToString
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Embedded
    private Category category;


    @ToString.Exclude
    @ElementCollection
    @CollectionTable(name = "postImages", joinColumns = @JoinColumn(name = "post_id"))
    private List<PostImage> postImagesList = new ArrayList<>();

    @ToString.Exclude
    @ElementCollection
    @CollectionTable(name = "postFiles", joinColumns = @JoinColumn(name = "post_id"))
    private List<PostFile> postFilesList = new ArrayList<>();

    @ColumnDefault("0")
    private int count;
}
