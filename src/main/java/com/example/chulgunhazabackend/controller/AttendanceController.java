package com.example.chulgunhazabackend.controller;

import com.example.chulgunhazabackend.dto.attendance.AttendanceCreateRequestDto;
import com.example.chulgunhazabackend.service.AttendanceRabbitMQService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceRabbitMQService attendanceRabbitMQService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAttendance(@Valid @RequestBody AttendanceCreateRequestDto attendanceCreateRequestDto){
        attendanceRabbitMQService.sendAttendanceDto(attendanceCreateRequestDto);
        return ResponseEntity.ok().body("성공");
    }


}
