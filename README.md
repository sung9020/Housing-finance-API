# 주택 금융 정보 조회 API  


- 구현 환경  
Spring boot  
Java 1.8  
Gradle  
Embedded tomcat  
H2 Database  

--- 

- 분석 데이터  
파일내용: 주택금융 신용보증 금융 기관별 공급현황  
파일명 : housing_finance_support_data.csv  
파일위치 : jar 빌드 파일(housingfinance-0.0.1-SNAPSHOT.jar)와 같은 위치에 csv 파일을 저장 해놓으면 분석이 가능하다.   

---

- 단위 테스트  
1. 기본 기능 테스트  
-> csv 읽기 테스트  
-> 은행 이름으로 DB에서 은행 코드 찾기  
-> 은행 리스트 조회  
-> 은행 주택 금융 지원 데이터 조회(전체 데이터)  
-> 연도별 은행별 지원금액 합계  
-> 최대 지원 금액에 해당하는 은행, 연도 구하기  
-> 외환은행의 연도별 지원 금액 중에서 최대 치소 연도, 금액 구하기  
-> 2018년 특정 은행 특정 달의 지원금액 예측하여 구하기  

2. Repository 테스트  
-> 주택금융 지원금액 데이터 DB에 넣기 테스트  
-> 은행 리스트 DB에 넣기 테스트  
-> 유저 정보 DB에 넣기 테스트  


3. Security 테스트  
-> 가입 테스트  
-> 가입 재시도 테스트  
-> 로그인 테스트  
-> 토큰 갱신 테스트  

---  

- 빌드 방법  
1. gradle 설치  
2. 프로젝트 루트 접근  
3. `gradle build` 명령어 실행  
4. 프로젝트 루트의 `build\libs` 위치에 jar 파일 생성 확인  

---  

- 구동 방법  
-> cmd나 쉘에서 아래 명령어를 입력  
-> `java -jar -Dspring.profiles.active=local housingfinance-0.0.1-SNAPSHOT.jar`(jar 파일 경로)  

---  

- 실행 가능한 릴리즈 파일  
-> https://github.com/sung9020/Housing-finance-API/releases  

---  

- API 명세를 보기위한 Swagger 주소  
-> localhost:9090/swagger-ui.html   

---  

- Swagger 테스트 시에 주의점  
-> 회원가입, 로그인 API 외에는 토큰 인증을 거쳐야 API 호출이 가능하다.  
-> 아래 스크린샷과 같이 Authorize 버튼을 누른 후에 Bearer <토큰> 을 넣어주면 API 호출이 가능해진다.  

![스웨거_테스트_주의2](https://user-images.githubusercontent.com/38482334/55668519-52e3ff80-58a6-11e9-9978-86185282aac2.JPG)
![스웨거_테스트_주의](https://user-images.githubusercontent.com/38482334/55641662-642afe80-5809-11e9-99e0-cb57e8937cfe.JPG)  

---  

- API 요청 프로세스  
-> 회원 가입 및 로그인을 하지 않으면 구현된 API를 호출 할 수 없다.  
-> 회원 가입을 위해 `localhost:9090/api/auth/signUp`을 호출하면 토큰이 발급된다.  
-> 이미 회원 가입을 하였으면, `localhost:9090/api/auth/signIn`에 username, password를 넣어서 호출하면 토큰이 발급된다.  
-> 회원 가입 이나 로그인 후에 Authorization 헤더에 Bearer <토큰> 형태로 넣어서 API를 호출하면 된다.  

---  

- 문제 해결 전략   
1. 파일을 저장하는 기준(엔티티)을 정하자 - 파일 파싱하여 연도, 월, 은행 형태로 엔티티를 정하여 DB에 싱크.  

2. 조회 시에는 JPQL로 연도, 은행 기준 1-12월의 지원금액 합계, 연도,은행 기준 1-12월 지원금액 평균을 구해서 데이터 활용   

3. 연도별 각 금융기관의 지원금액 합계 구하기 - 금융기관 지원금액을 전부 돌면서 연도의 지원금액 합계 데이터가 존재하면 업데이트하고, 존재하지 않으면 새로 해당 연도의 지원금액 합계 데이터를 데이터를 만들어서 넣는 방식으로 진행   

4. 연도별 금융기관의 전체 지원금액 중에서 가장 큰 금액의 기관명 출력 - 연도별 1-12월의 금융기관별 지원금액 합계 데이터를 DB 질의를 통해 구한 후, 그중에서 비교를 통해 가장 큰 값을 구하면 된다.(Java 8 Stream의 max 함수 사용)   

5. 연도별 외환은행의 지원금액 평균 중에서 가장 큰값 작은값 구하기 - 연도별 1-12월의 금융기관별 지원금액 평균 데이터를 DB 질의를 통해 구한 후, 그중에서 비교를 통해 가장 큰값, 작은 값을 구하면 된다.(Java 8 Stream의 max, min 함수 사용)   
 
6. 대량의 합 및 평균 연산(엑셀 연산과 유사한)을 할때는 DB에서 질의를 통해 구하는 것이 매우 효율적.  

7. 다음년도 특정은행 특정달의 지원 금액 예측은 다항식커브피팅을 사용. 그간의 추이를 분석하여 가장 근접한 값을 분석하기에 좋은 알고리즘으로 판단.  

8. 다항식 커브피팅은 y = a0 + a1 * x + a2 * x^2.. 을 구성하는 다항식에서 가장 적합한 a 값을 찾아내는 문제로 풀린다.  

9. x축은 x, y축은 t라는 2차원 그래프에서 (y(x, a)-학습샘플좌표(x, t)) 값을 최소로 만드는 a 값을 구했고 다음 예측하고 싶은 값이 b라고 하면 y(b, a)를 계산 하면 될 것이다.  

---

소스 내 체크 리스트  
- 지원 금액 합계 Max값 Optional 검사  
- 지원 금액 합계 데이터 로딩 시에 Map을 생성자로 뺴기
- jwt 생성 시 토큰 명세서 수정
- 토큰 갱신 이슈 시에 해결 방법(토큰마다 고유 ID 지정)
