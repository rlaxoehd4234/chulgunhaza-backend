package com.example.chulgunhazabackend.controller;

import com.example.chulgunhazabackend.dto.PageDto;
import com.example.chulgunhazabackend.dto.chat.ChatMessageCreateRequestDto;
import com.example.chulgunhazabackend.dto.chat.ChatMessageListResponseDto;
import com.example.chulgunhazabackend.dto.chat.ChatRoomCreateRequestDto;
import com.example.chulgunhazabackend.dto.chat.ChatRoomListResponseDto;
import com.example.chulgunhazabackend.service.ChatMessageService;
import com.example.chulgunhazabackend.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @PostMapping("/create")
    public ResponseEntity<Long> createRoom(@RequestBody ChatRoomCreateRequestDto chatRoomCreateRequestDto){
        return ResponseEntity.ok().body(chatRoomService.saveChatRoom(chatRoomCreateRequestDto));
    }

    @GetMapping("/find/rooms/{employeeId}")
    public ResponseEntity<PageDto<ChatRoomListResponseDto>> findAllByEmployeeId(@PathVariable Long employeeId, Pageable pageable){
        return ResponseEntity.ok().body(chatRoomService.getAllChatRoomsByEmployeeId(employeeId, pageable));
    }

    @PostMapping("/send")
    public ResponseEntity<Long> createMessage(@Valid @RequestBody ChatMessageCreateRequestDto chatMessageCreateRequestDto){
        return ResponseEntity.ok().body(chatMessageService.saveChatMessage(chatMessageCreateRequestDto));
    }

    @GetMapping("/find/{chatRoomId}")
    public ResponseEntity<PageDto<ChatMessageListResponseDto>> findChatMessage(@PathVariable Long chatRoomId, @PageableDefault(30) Pageable pageable){
        return ResponseEntity.ok().body(chatMessageService.getChatMessagesByRoomId(chatRoomId, pageable));
    }

}
