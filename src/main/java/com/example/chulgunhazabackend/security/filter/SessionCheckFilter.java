package com.example.chulgunhazabackend.security.filter;

import com.example.chulgunhazabackend.dto.Employee.EmployeeCredentialDto;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeException;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeExceptionType;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
            String password = (String) session.getAttribute("password");
            String name = (String) session.getAttribute("name");
            Long employeeNo = (Long) session.getAttribute("employeeNo");
            List<String> roles = (List<String>) session.getAttribute("roles");
            String department = (String) session.getAttribute("department");

            EmployeeCredentialDto employeeCredentialDto = new EmployeeCredentialDto(
                    id, email, password, name, employeeNo, roles, department
            );

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(employeeCredentialDto, password, employeeCredentialDto.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        }else {
            log.warn("세션 쿠키가 없습니다.");

            Gson gson = new Gson();

            String jsonStr = gson.toJson(Map.of("error", "로그인을 먼저 진행해 주세요."));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonStr);
            printWriter.close();
        }

    }



}
