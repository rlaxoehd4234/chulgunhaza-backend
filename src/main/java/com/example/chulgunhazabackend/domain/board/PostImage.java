package com.example.chulgunhazabackend.domain.board;

import com.example.chulgunhazabackend.domain.common.BaseEntity;
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
public class PostImage extends BaseEntity {

    private String imageName;

    private String imagePath;

    private Long imageSize;

    private String imageType;

    private int ord; // 파일의 순번

    public void setOrd(int ord){
        this.ord = ord;
    }


}
