package com.example.chulgunhazabackend.repository;

import com.example.chulgunhazabackend.domain.annual.Annual;
import com.example.chulgunhazabackend.domain.member.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void saveEmployee(){
        EmployeeImage employeeImage = new EmployeeImage("imageName", "imagePath", 1L, "JPG");
        Annual annual = new Annual();
        List<UserRole> userRoles = List.of(UserRole.USER, UserRole.ADMIN);
        Employee employee = new Employee(
                "Kim", "aaa@naver.com20", Gender.MALE, LocalDate.of(2025, 1, 16),
                LocalDate.of(2025, 1, 16), null, "인사과", Position.EMPLOYEE,
                userRoles, employeeImage, annual
        );
        employeeRepository.save(employee);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void saveEmployees(){
        EmployeeImage employeeImage = new EmployeeImage("imageName", "imagePath", 1L, "JPG");
        Annual annual = new Annual();
        List<UserRole> userRoles = List.of(UserRole.USER, UserRole.ADMIN);
        
        for(int i=0; i<500; i++){
            Employee employee = new Employee(
                    "Kim", "aaa@naver.com5555" + i, Gender.MALE, LocalDate.of(2025, 1, 16),
                    LocalDate.of(2025, 1, 16), null, "인사팀", Position.EMPLOYEE,
                    userRoles, employeeImage, annual
            );
            employeeRepository.save(employee);
        }

        for(int i=0; i<500; i++){
            Employee employee = new Employee(
                    "Lim", "aaa@naver.com1" + i, Gender.MALE, LocalDate.of(2025, 1, 16),
                    LocalDate.of(2025, 1, 16), null, "기획팀", Position.EMPLOYEE,
                    userRoles, employeeImage, annual
            );
            employeeRepository.save(employee);
        }


        for(int i=0; i<500; i++){
            Employee employee = new Employee(
                    "Chae", "aaa@naver.com2" + i, Gender.MALE, LocalDate.of(2025, 1, 16),
                    LocalDate.of(2025, 1, 16), null, "소프트웨어 팀", Position.EMPLOYEE,
                    userRoles, employeeImage, annual
            );
            employeeRepository.save(employee);
        }
        
    }




}