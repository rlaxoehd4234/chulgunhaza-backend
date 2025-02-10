package com.example.chulgunhazabackend.service;

import com.example.chulgunhazabackend.dto.attendance.AttendanceCreateRequestDto;

public interface AttendanceService {

    void registerAttendance(AttendanceCreateRequestDto attendanceCreateRequestDto);
}
