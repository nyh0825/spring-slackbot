package com.example.slackbot.service;

import com.example.slackbot.entity.Alert;
import com.example.slackbot.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    private final AlertRepository repo;

    public AlertService(AlertRepository repo) {
        this.repo = repo;
    }

    public void addAlert(String message, LocalDateTime time) {
        Alert alert = new Alert(message, time);
        repo.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return repo.findAll();
    }

    public Optional<Alert> getAlert(Long id) {
        return repo.findById(id);
    }

    public void updateAlert(Long id, String message, LocalDateTime time) {
        Alert alert = repo.findById(id).orElseThrow();
        alert.setMessage(message);
        alert.setTime(time);
        repo.save(alert);
    }

    public void deleteAlert(Long id) {
        repo.deleteById(id);
    }

    // 🔥 슬랙 전송 대상만 추출
    public List<Alert> getPendingAlertsToSend() {
        return repo.findByTimeBeforeAndStatus(LocalDateTime.now(), "대기");
    }

    // 🔥 전송 완료 처리
    public void markAsCompleted(Alert alert) {
        alert.setStatus("완료"); // ← 여기서 setStatus가 안 되면 Alert 클래스에 문제가 있음
        repo.save(alert);
    }
}
