package com.example.slackbot.scheduler;

import com.example.slackbot.entity.Alert;
import com.example.slackbot.service.AlertService;
import com.example.slackbot.service.SlackService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // 🔥 스프링이 관리하는 컴포넌트(=Bean)로 등록 (자동으로 실행될 놈)
public class SlackScheduler {

    private final AlertService alertService; // 🔥 알림 가져오고 상태 업데이트하는 서비스
    private final SlackService slackService; // 🔥 슬랙 메시지 보내는 서비스

    // 🔥 생성자 주입 (DI: 의존성 주입)
    public SlackScheduler(AlertService alertService, SlackService slackService) {
        this.alertService = alertService;
        this.slackService = slackService;
    }

    @Scheduled(cron = "0 * * * * *") // 🔥 매 **매분 0초**에 실행 (ex. 12:00:00, 12:01:00, 12:02:00 ...)
    public void sendSlackAlerts() {
        // 🔥 보내야 할 알림 리스트 가져오기 (현재시간 이전 + "대기" 상태인 알림들)
        List<Alert> alerts = alertService.getPendingAlertsToSend();

        for (Alert alert : alerts) {
            // 🔥 슬랙에 보낼 메세지 형식 만들기
            String msg = "🔔 알림: " + alert.getMessage() + " (" + alert.getTime() + ")";

            // 🔥 슬랙으로 메세지 발사!
            slackService.sendMessage(msg);

            // 🔥 발사 끝난 알림은 상태를 "완료"로 바꿔줌
            alertService.markAsCompleted(alert);
        }
    }
}
