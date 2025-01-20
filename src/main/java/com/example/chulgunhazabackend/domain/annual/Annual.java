package com.example.chulgunhazabackend.domain.annual;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Embeddable
@Getter
@ToString
public class Annual {

    private double totalAnnualCount;

    private double useCount;

    private double remainingAnnualCount;

    private double sickAnnualCount;

    public Annual() {
        this.totalAnnualCount = 15.0;
        this.useCount = 0.0;
        this.remainingAnnualCount = 15.0;
        this.sickAnnualCount = 0.0;
    }

    @Builder
    public Annual(double totalAnnualCount, double useCount,
                  double remainingAnnualCount, double sickAnnualCount) {
        this.totalAnnualCount = totalAnnualCount;
        this.useCount = useCount;
        this.remainingAnnualCount = remainingAnnualCount;
        this.sickAnnualCount = sickAnnualCount;
    }

    // 연차가 수정되었을 때 사용하는 생성자
}
