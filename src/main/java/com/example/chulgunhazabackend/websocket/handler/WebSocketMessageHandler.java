package com.example.chulgunhazabackend.websocket.handler;

import com.example.chulgunhazabackend.dto.Employee.EmployeeCredentialDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketMessageHandler implements WebSocketHandler {

    private static final ConcurrentHashMap<Long, ConcurrentHashMap<Long, WebSocketSession>> sessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // INFO : 인증된 유저의 정보를 가져와서 ConcurrentHashMap 에 저장을 합니다.
        // HACK : 우선 웹 소캣 통신에 대한 기반을 잡아두고 Redis Session Storage 에 저장하는 로직을 작성 예정, 다만 Emitter 처럼 Redis 에 직렬화 해서 넣기가 애매 함.
        SecurityContext securityContext = (SecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
        if(securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if(authentication != null && authentication.isAuthenticated()) {
                EmployeeCredentialDto credentialDto = (EmployeeCredentialDto) authentication.getPrincipal();
                sessions.put(credentialDto.getId(), new ConcurrentHashMap<>());
                log.info("Websocket Connected for user " + credentialDto.getId());
            }else {
                session.close(CloseStatus.NOT_ACCEPTABLE);
                log.warn("Failed to connect to websocket, no Authentication");
            }
        }else{
            session.close(CloseStatus.NOT_ACCEPTABLE);
            log.warn("WebSocket connection attempt failed. SecurityContext is missing.");
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //INFO : Client 로 부터 받은 메시지를 처리할 수 있음.
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //INFO : Client 로 부터 받은 메시지에 에러가 발생했을 때 처리할 수 있음.
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        SecurityContext securityContext = (SecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                EmployeeCredentialDto credentialDto = (EmployeeCredentialDto) authentication.getPrincipal();
                sessions.remove(credentialDto.getEmployeeNo()); // 세션 종료 시 제거
                log.info("WebSocket connection closed for user: {}", credentialDto.getEmployeeNo());
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public WebSocketSession getSession(long employeeNo, Long roomId) {
        return sessions.get(employeeNo).get(roomId);
    }
}
