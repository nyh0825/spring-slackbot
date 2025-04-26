package com.example.slackbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity // 🔥 이 클래스가 DB 테이블이 된다 (JPA 엔티티)
@Getter // 🔥 롬복: getter 자동 생성 (id, message, time, status에 대해)
@Setter // 🔥 롬복: setter 자동 생성
@NoArgsConstructor // 🔥 롬복: 기본 생성자 생성 (아무 인자 없는 생성자)
@AllArgsConstructor // 🔥 롬복: 모든 필드 갖는 생성자 생성
public class Alert {

    @Id // 🔥 기본키(PK) 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🔥 AUTO_INCREMENT처럼 DB가 알아서 id 채워줌
    private Long id; // 알림 고유 ID

    private String message; // 알림 내용

    private LocalDateTime time; // 알림 예정 시간

    private String status = "대기"; // 알림 상태 (초기값: "대기")

    // 🔥 메시지 + 시간만 받는 생성자 (상태는 자동으로 "대기"로 세팅)
    public Alert(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
        this.status = "대기"; // 직접 세팅
    }
}
