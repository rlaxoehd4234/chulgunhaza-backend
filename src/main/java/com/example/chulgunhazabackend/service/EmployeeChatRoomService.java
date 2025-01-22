package com.example.chulgunhazabackend.service;

import com.example.chulgunhazabackend.domain.chat.ChatRoom;
import com.example.chulgunhazabackend.domain.chat.EmployeeChatRoom;
import com.example.chulgunhazabackend.domain.member.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeChatRoomService {

    Long save(ChatRoom chatRoom, Employee senderEmployee, Employee receiverEmployee);

    Page<EmployeeChatRoom> findByEmployeeId(Long employeeId, Pageable pageable);

}
