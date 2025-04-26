package com.example.slackbot.repository;

import com.example.slackbot.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

// 🔥 DB랑 통신하는 놈 (Alert 테이블과 연결되는 인터페이스)
public interface AlertRepository extends JpaRepository<Alert, Long> {

    // 🔥 현재 시간(now) 이전에 설정된 모든 알림 가져오기
    // ex) "지금 시간보다 예전에 예약된 알림들"
    List<Alert> findByTimeBefore(LocalDateTime now);

    // 🔥 현재 시간 이전 + 상태(status)가 특정 값인 알림 가져오기
    // ex) "지금 시간 이전이고, 상태가 '대기'인 알림만 뽑아라"
    List<Alert> findByTimeBeforeAndStatus(LocalDateTime now, String status);
}
