package com.example.chulgunhazabackend.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostFile extends BaseEntity {

    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    private int ord; // 파일의 순번

    public void setOrd(int ord){
        this.ord = ord;
    }

}
