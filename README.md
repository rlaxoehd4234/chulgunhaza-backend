## 출근하자! 
출퇴근 기록과 실시간 소통 기능을 강화해 효율적이고 원활한 업무 환경을 제공합니다. 직원들이 편리하게 소통하고 근무를 관리할 수 있는 간단한 그룹웨어 시스템입니다. 

## 기간
2025.01 ~ 진행 중


## 프로젝트 목표
- 직원 관리
- 실시간 소통 및 알림 시스템
- 연차 및 근무 시간 관리
- 근태 현황 시각화
- 관리자와 근태 담당자의 업무 효율화
- 동시성 제어
- 데이터베이스 샤딩
- 부하 테스트


## 주요 기능
- 실시간 채팅 - WebSocket, Redis, Message Queue 사용 
- 페이징 처리 
- 권한 관리 - Admin, 근태 담장자, 사원 분리 
- 파일 업로드 - MultiPartFile 사용 추후 S3 마이그레이션 
- 동시성 제어 - 연차 사용 동시성 제어
- 트래픽 처리- Message Queue를 활용한 트래픽 처리
- 정산 처리 - Spring Batch를 활용한 출근 정산 
- 로그인 - 세션 로그인  
- 소프트 딜리트 - delFlag 를 필드를 통해 소프트 딜리트
- 레플리카 - Redis 및 DB 다운 시 복구 서버 구축
- 로그 관리 - AOP 를 활용해 로그 추적 및 스케쥴러로 파일 관리

## 기술
![Java 17](https://img.shields.io/badge/Java-17-blue)
![Spring 3.x](https://img.shields.io/badge/Spring-3.x.x-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.x-006F39)
![Data JPA](https://img.shields.io/badge/Data%20JPA-2.x-0076B3)
![Spring Security](https://img.shields.io/badge/Spring%20Security-5.x-6DB33F)
![Spring Batch](https://img.shields.io/badge/Spring%20Batch-4.x-0062A1)
![Redis](https://img.shields.io/badge/Redis-6.x-DC382D)
![Docker](https://img.shields.io/badge/Docker-20.x-blue)
![Nginx](https://img.shields.io/badge/Nginx-1.x-lightgray)
![Git](https://img.shields.io/badge/Git-2.x-F05032)
![Swagger](https://img.shields.io/badge/Swagger-API%20Docs-brightgreen)
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-lightgrey)


## 팀원 
|임솔|김태동|
|-------------------|---------------------------|
| <img src="https://github.com/user-attachments/assets/cb9eb08e-0cff-4c0c-9637-836e0d2fcac2" width="200" height="200">| <img src="https://github.com/user-attachments/assets/7b47759d-0325-46f2-bb7f-188f222ac894" width="200" height="200">|
| 백엔드 개발 | 백엔드 개발 |
| [깃허브 링크](https://github.com/saulsol)    | [깃허브 링크](https://github.com/rlaxoehd4234) |
