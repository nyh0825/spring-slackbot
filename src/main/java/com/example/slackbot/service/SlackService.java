package com.example.slackbot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlackService {

    private static final String WEBHOOK_URL = "https://hooks.slack.com/services/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"; // ← 이거 네 꺼로 교체

    public void sendMessage(String text) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> payload = new HashMap<>();
            payload.put("text", text);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(WEBHOOK_URL, request, String.class);
            System.out.println("✅ 슬랙 응답: " + response.getStatusCode() + " - " + response.getBody());
        } catch (Exception e) {
            System.out.println("❌ 슬랙 메시지 전송 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
