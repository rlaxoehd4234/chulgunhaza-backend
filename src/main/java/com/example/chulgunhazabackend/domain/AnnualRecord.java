package com.example.chulgunhazabackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="AnnualRecords")
@Getter
public class AnnualRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_record_id")
    private Long id;

    @Column(nullable = false)
    private Long approvedId;

    @Column(nullable = false)
    private LocalDate annualDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnualType annualType;

    private String annualReason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnualApprovalStatus annualApprovalStatus;

}
