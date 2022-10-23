# DZ_Chat
- 개발 기간 : 2022.10.13 ~ 2022.10.17
- 보수 기간 : ~ 2022.10.23

Java Socket 을 이용한 채팅 프로그래밍

## 주요 기능
### 회원
- 회원가입
- 회원정보 수정
- 로그인
- 비밀번호 찾기
- 회원탈퇴
### 채팅
- 채팅방 만들기
- 채팅방 입장
- 채팅방 나가기
- 채팅방 자동 삭제(인원 0명 시)
- 귓속말
- 일반 채팅
### FTP
- 채팅방 내 파일 전송(#fileSend)
- 채팅방 내 파일 다운로드(#fileSave)
![image](https://user-images.githubusercontent.com/25365672/197394323-0b5a4724-7e1c-4940-ace6-a8ad5afe5b0a.png)
- 파일 전송 중 취소 기능(#fileStop)
![image](https://user-images.githubusercontent.com/25365672/197394552-979af8f9-a150-4b28-adfb-7fbf5fe73ed6.png)
![image](https://user-images.githubusercontent.com/25365672/197394466-09c28771-0a25-4b87-87f2-eaac0f1f6aeb.png)

- 채팅방 파일 목록 조회(#dir)

### 로그
- 서버에서 채팅 내용 저장
- 회원 관리 기록 저장
### 서버 기능
- 클래스 파일 변경 시 서버 자동 재실행
- 서버 재실행 시 채팅 접속 클라이언트 자동 재접속
- 서버 재실행 시 전송 실패된 메세지 재전송
- 서버 재실행 시 로깅 
### 환경설정
- 기능 확장을 위해 Property 속성 파일 사용
