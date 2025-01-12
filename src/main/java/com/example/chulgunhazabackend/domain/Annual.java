package com.example.chulgunhazabackend.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Annual {

    private double totalAnnualCount;

    private double useCount;

    private double remainingAnnualCount;

    private LocalDate annualDate;

    private double sickAnnualCount;

}
