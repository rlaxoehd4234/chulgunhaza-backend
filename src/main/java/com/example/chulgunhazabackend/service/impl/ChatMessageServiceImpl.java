package com.example.chulgunhazabackend.service.impl;

import com.example.chulgunhazabackend.domain.chat.ChatMessage;
import com.example.chulgunhazabackend.domain.chat.ChatRoom;
import com.example.chulgunhazabackend.domain.chat.EmployeeChatRoom;
import com.example.chulgunhazabackend.domain.member.Employee;
import com.example.chulgunhazabackend.dto.PageDto;
import com.example.chulgunhazabackend.dto.chat.ChatMessageCreateRequestDto;
import com.example.chulgunhazabackend.dto.chat.ChatMessageListResponseDto;
import com.example.chulgunhazabackend.dto.chat.ChatNotificationDto;
import com.example.chulgunhazabackend.exception.chatException.ChatException;
import com.example.chulgunhazabackend.exception.chatException.ChatExceptionType;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeException;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeExceptionType;
import com.example.chulgunhazabackend.repository.ChatMessageRepository;
import com.example.chulgunhazabackend.repository.ChatRoomRepository;
import com.example.chulgunhazabackend.repository.EmployeeChatRoomRepository;
import com.example.chulgunhazabackend.repository.EmployeeRepository;
import com.example.chulgunhazabackend.service.ChatMessageService;
import com.example.chulgunhazabackend.service.ChatRabbitMQMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeChatRoomRepository employeeChatRoomRepository;
    private final ChatRabbitMQMessageService chatRabbitMQMessageService;

    @Override
    public Long saveChatMessage(ChatMessageCreateRequestDto chatMessageCreateRequestDto) {

        // INFO: 채팅방 존재 유무 확인
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageCreateRequestDto.getRoomId()).orElseThrow(() -> new ChatException(ChatExceptionType.NOT_FOUND_CHAT_ROOM));

        // INFO: 전송하는 사원의 유무 확인
        // HACK : employeeNO 로 찾아오는 로직으로 변경 예정
        Employee employee = employeeRepository.findEmployeeById(chatMessageCreateRequestDto.getSenderId()).orElseThrow(() -> new EmployeeException(EmployeeExceptionType.NOT_EXIST_USER));

        // INFO: 채팅방 인원이 아닌데 전송하는 경우 예외 처리
        // HACK: employeeNO 로 찾아오는 로직으로 변경 예정
        EmployeeChatRoom employeeChatRoom = employeeChatRoomRepository.findByEmployeeIdAndChatRoomId(employee.getId(), chatRoom.getId()).orElseThrow(() -> new ChatException(ChatExceptionType.NOT_FOUND_CHAT_USER));

        // HACK: 오프라인 유저, 온라인 유저 구분 로직 추가 시 로직 변경 예정
        chatRabbitMQMessageService.sendNotification(new ChatNotificationDto(employee.getEmployeeNo(), employee.getName(), employee.getDepartment(), employee.getPosition(), employee.getEmployeeNo() + " 님 에게서 메시지가 도착했습니다."));

        return chatMessageRepository.save(chatMessageCreateRequestDto.toEntity(chatRoom, employee)).getId();
    }

    @Override
    public PageDto<ChatMessageListResponseDto> getChatMessagesByRoomId(Long roomId, Pageable pageable) {

        // 채팅방의 존재 유무
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new ChatException(ChatExceptionType.NOT_FOUND_CHAT_ROOM));

        Page<ChatMessage> contents = chatMessageRepository.findByChatRoomOrderByCreatedAtDesc(chatRoom, pageable);

        Page<ChatMessageListResponseDto> pageDto = contents.map(chatMessage -> new ChatMessageListResponseDto().fromEntity(chatMessage));

        return new PageDto<>(pageDto) ;
    }
}
