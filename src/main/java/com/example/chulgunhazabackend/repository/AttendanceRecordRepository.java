package com.example.chulgunhazabackend.repository;

import com.example.chulgunhazabackend.domain.attendance.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

}
