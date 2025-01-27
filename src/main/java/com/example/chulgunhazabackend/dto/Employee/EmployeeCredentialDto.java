package com.example.chulgunhazabackend.dto.Employee;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeCredentialDto extends User {

    private Long id;

    private String email;

    private String password;

    private String name;

    private Long employeeNo;

    private List<String> roles;

    private String department;

    public EmployeeCredentialDto(Long id, String email, String password, String name, Long employeeNo,
                                 List<String> roles, String department) {
        super(email, password, roles.stream().map(
                str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList())
        );
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.employeeNo = employeeNo;
        this.roles = roles;
        this.department = department;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", id);
        dataMap.put("email", email);
        dataMap.put("password", password);
        dataMap.put("name", name);
        dataMap.put("employeeNo", employeeNo);
        dataMap.put("roles", roles);
        dataMap.put("department", department);

        return dataMap;
    }
}
