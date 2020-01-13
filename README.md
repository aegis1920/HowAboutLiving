# HowAboutLiving

## 프로젝트 개요

현재 위치를 기반으로 주변의 위험을 파악하는 서비스

- Spring Batch를 사용해보는 목적으로 개발

## 프로젝트 아키텍처

### 프로젝트 전체 아키텍처

![프로젝트 아키텍처](/img/pjt-arch.PNG)

### Spring Scheduler 활용

![spring-scheduler](/img/spring-scheduler.PNG)

### Spring Batch 활용

![spring-batch](/img/spring-batch.PNG)

## 프로젝트 실행

Spring boot 안에 프론트엔드가 있어 boot를 실행시키기만 하면 끝

- 프론트엔드를 수정했다면 `npm run build`를 통해 Spring Boot에 빌트인 시켜야함

## 기술스택

IDE

- STS 4.4.2

기술스택

2. java 1.8
3. mariadb 10.4
4. Mybatis 3.3.0
5. Spring boot 2.2.2
6. Spring batch
7. javax mail 1.6.2

## 기능

- Spring Scheduler를 통해 공공데이터를 지속적으로 사용
- Spring Batch를 통한 Read와 Process 과정
- 공공데이터를 못 받아올 때 관리자에게 이메일 발송

## 고민했던 부분

1. 공공데이터를 받아올 때 DB Field가 비어있거나 -로 올 때
    1. 평균계산할 때는 아예 빼고 계산
2. 공공데이터를 못 받아올 때
    1. 최근 일주일간의 평균값으로 DB에 적재 후, 잘못된 부분을 관리자(나)에게 이메일 발송