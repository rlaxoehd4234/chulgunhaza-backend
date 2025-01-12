package com.example.chulgunhazabackend.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employees")
@Getter
@ToString(exclude = "userRoleList")
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Long employeeNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Column
    private LocalDate resignationDate;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String position;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "userRoles", joinColumns = @JoinColumn(name = "employee_id"))
    private List<UserRole> userRoleList = new ArrayList<>();

    @Embedded
    private EmployeeImage employeeImage;

    @Embedded
    private Annual annual;

    public void addRole(UserRole userRole){
        userRoleList.add(userRole);
    }

    public void clearRole(){
        userRoleList.clear();
    }

}
