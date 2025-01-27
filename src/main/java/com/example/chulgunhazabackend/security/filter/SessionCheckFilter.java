package com.example.chulgunhazabackend.security.filter;

import com.example.chulgunhazabackend.dto.Employee.EmployeeCredentialDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info(path);

        if(path.startsWith("/v1/employee/login")){
            return true;
        }


        return false; // false(체크) / true(체크X)
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal");

        HttpSession session = request.getSession(false);

        if(session != null){
            Long id = (Long) session.getAttribute("id");
            String email = (String) session.getAttribute("email");
            String name = (String) session.getAttribute("name");
            String pw = (String) session.getAttribute("pw");
            Long employeeNo = (Long) session.getAttribute("employeeNo");
            List<String> roles = (List<String>) session.getAttribute("roles");
            String department = (String) session.getAttribute("department");

            EmployeeCredentialDto employeeCredentialDto = new EmployeeCredentialDto(
                    id, email, name, pw, employeeNo, roles, department
            );

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(employeeCredentialDto, pw, employeeCredentialDto.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        }else {
            log.warn("세션 쿠키 없어");
            throw new RuntimeException();
        }

    }



}
