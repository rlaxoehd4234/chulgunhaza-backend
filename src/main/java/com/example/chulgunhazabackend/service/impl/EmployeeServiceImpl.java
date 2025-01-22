package com.example.chulgunhazabackend.service.impl;

import com.example.chulgunhazabackend.domain.annual.Annual;
import com.example.chulgunhazabackend.domain.member.Employee;
import com.example.chulgunhazabackend.domain.member.EmployeeImage;
import com.example.chulgunhazabackend.dto.Employee.EmployeeListResponseDto;
import com.example.chulgunhazabackend.dto.Employee.EmployeeModifyRequestDto;
import com.example.chulgunhazabackend.dto.Employee.EmployeeResponseDto;
import com.example.chulgunhazabackend.dto.Employee.EmployeeCreateRequestDto;
import com.example.chulgunhazabackend.dto.PageDto;
import com.example.chulgunhazabackend.event.employee.event.EmployeeCreateEvent;
import com.example.chulgunhazabackend.event.employee.event.EmployeeModifyEvent;
import com.example.chulgunhazabackend.event.common.Events;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeException;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeExceptionType;
import com.example.chulgunhazabackend.repository.EmployeeRepository;
import com.example.chulgunhazabackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final LocalFileServiceImpl fileService;

    // 사원 최초 생성 시 기본이미지, 기본 연차 생성

    public Long create(EmployeeCreateRequestDto employeeCreateRequestDto) throws IOException{
        Events.raise(new EmployeeCreateEvent(1L, 10124024L)); // 대상자, 진행하는 사람
        checkEmail(employeeCreateRequestDto.getEmail());
        EmployeeImage employeeImage = EmployeeImage.builder()
                .imageName("default image")
                .build(); // 기본 이미지를 사용한다고 지정

        Employee employee = employeeCreateRequestDto.toEntity(employeeImage, new Annual());
        Employee saved = employeeRepository.save(employee);
        System.out.println("생성됨");
        System.out.println(saved.getId());

        return saved.getId();
    }

    // 사원 목록 조회
    @Transactional(readOnly = true)
    public PageDto<?> getEmployeeList(Pageable pageable){
        Page<EmployeeListResponseDto> contents = employeeRepository.selectEmployeeList(pageable)
                .map(EmployeeListResponseDto::fromEntity);
        return new PageDto<>(contents);

    }
    // 사원 조회
    @Transactional(readOnly = true)
    @Override
    public EmployeeResponseDto getEmployee(Long id) {
        Employee employee = employeeRepository.findEmployeeById(id).orElseThrow(()
                -> new EmployeeException(EmployeeExceptionType.NOT_EXIST_USER));
        return EmployeeResponseDto.fromEntity(employee);
    }


    // 사원 정보 수정
    // TODO: 사원 파일 이미지 저장 서비스 추가 예정
    @Transactional(rollbackFor = IOException.class)
    public EmployeeResponseDto modifyById(Long id, EmployeeModifyRequestDto employeeModifyRequestDto,
                                          MultipartFile image) throws IOException {
        Events.raise(new EmployeeModifyEvent(1L, 10124024L));

        Employee findEmployee = employeeRepository.findEmployeeByIdForUpdate(id).orElseThrow(()
                -> new EmployeeException(EmployeeExceptionType.NOT_EXIST_USER));

        if(!employeeModifyRequestDto.getEmail().equals(findEmployee.getEmail())){
            checkEmail(employeeModifyRequestDto.getEmail());
        }

        checkVersion(findEmployee, employeeModifyRequestDto.getVersion());

        findEmployee.updateEmployee(employeeModifyRequestDto, checkImageIsNull(image));
        Employee saved = employeeRepository.saveAndFlush(findEmployee);

        return EmployeeResponseDto.fromEntity(saved);
    }


    public Long deleteById(Long id){
        Employee findEmployee = employeeRepository.findEmployeeByIdForUpdate(id).orElseThrow(()
                -> new EmployeeException(EmployeeExceptionType.NOT_EXIST_USER));
        findEmployee.delete();
        return employeeRepository.save(findEmployee).getId();
    }
    public void checkEmail(String email){
        Optional<Employee> employeeEmail = employeeRepository.findEmployeeByEmail(email);
        if(employeeEmail.isPresent()){
            throw new EmployeeException(EmployeeExceptionType.ALREADY_EXIST_EMAIL);
        }
    }

    private void checkVersion(Employee findEmployee, long version){
        if(!findEmployee.matchVersion(version)){
            throw new EmployeeException(EmployeeExceptionType.ALREADY_CHANGED);
        }
    }


    private EmployeeImage checkImageIsNull(MultipartFile multipartFile){

        //TODO: 파일 서비스 추가 예정

        if(multipartFile.isEmpty()){
            return EmployeeImage.builder()
                    .imageName("default image")
                    .build(); // 기본 이미지를 사용한다고 지정
        }else {
            return EmployeeImage.builder()
                    .imageName("modified image")
                    .build(); // 변경된 이미지 지정
        }
    }


}
