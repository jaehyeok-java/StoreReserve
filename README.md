# 매장 예약 서비스 (Store Reservation Service)
Spring Boot 기반의 매장 예약 서비스

##  Use 
- **Backend**: Spring Boot, JPA, MySQL,
- **Authentication**: JWT, Spring Security  
- **Infra**: Docker(미사용)

##  Goal 
매장 예약 및 리뷰 기능을 제공하는 서비스 구축  
- 점장은 매장을 등록하고 관리 가능  
- 사용자는 매장을 검색하고 예약 가능  
- 예약 후 방문 확인 및 리뷰 작성 가능  

---

##  회원 (User)
### 공통
- [x] 이메일 인증을 통한 회원가입  

### 고객 (User)
- [x] 회원가입  
- [x] 이메일 인증  
- [x] 로그인 & JWT 토큰 발급  
- [x] JWT를 활용한 인증 처리   

### 점장 (Owner)
- [x] 회원가입  

---

##  매장 관리 (Store)
### 점장 (Store Owner)
- [x] 매장 등록   
- [x] 매장 삭제  

### 고객 (User)
- [x] 매장 검색  
- [x] 매장 상세 정보 확인  

---

##  예약 (Reservation)
### 고객 (User)
- [x] 예약 요청    
- [x] 예약 10분 전 키오스크 방문 확인 

---

##  리뷰 (Review)
- [x] 예약 완료 후 리뷰 작성  
- [x] 리뷰 수정  (작성자만 가능)
- [x] 리뷰 삭제 (작성자 또는 점장만 가능)  

