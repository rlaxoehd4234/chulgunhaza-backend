package com.example.chulgunhazabackend.domain.member;

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
public class EmployeeImage {

    private String imageName;

    private String imagePath;

    private Long imageSize;

    private String imageType;

}
