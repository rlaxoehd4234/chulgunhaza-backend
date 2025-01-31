package com.example.chulgunhazabackend.service.impl;

import com.example.chulgunhazabackend.dto.chat.ChatNotificationDto;
import com.example.chulgunhazabackend.service.ChatAlarmService;
import com.example.chulgunhazabackend.service.sse.SseEmitterManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


//HACK : 추 후 유실가능성이 있는 Sse Alarm 에 대해 처리가 필요합니다.
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatAlarmServiceImpl implements ChatAlarmService {

    private final SseEmitterManager sseEmitterManager;

    //INFO: Chat Sse 를 구독하는 로직입니다. 세부 로직 -> SseEmitterManager
    @Override
    public SseEmitter subscribe(Long employeeNo) throws IOException {
        return sseEmitterManager.chatRegisterEmitter(employeeNo);
    }

    //INFO : 구독 중이라면 전송하는 로직입니다.
    @Async
    @Override
    public void sendSseEvent(ChatNotificationDto chatNotificationDto) {
        SseEmitter emitter = sseEmitterManager.getChatEmitter(chatNotificationDto.getEmployeeNo());
        if (emitter != null){
            try{
                // HACK: 신규 구독 시 쌓여 있던 알람을 보내주는 로직을 추가합니다.
                emitter.send(SseEmitter.event().data(chatNotificationDto));
                log.info("send from ChatAlarmService Send sendSseEvent Method");
            } catch (IOException e) {
                log.info("Occur error from ChatAlarmService Send sendSseEvent Method");
                sseEmitterManager.removeChatEmitter(chatNotificationDto.getEmployeeNo());
            }
        }else {
            //HACK: 구독중이 아니라면 DB에 알람을 저장을 저장하는 로직을 추가합니다.
            log.info("emitter is null");
        }
    }
}
