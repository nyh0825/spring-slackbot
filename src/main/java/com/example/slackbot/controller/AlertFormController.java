package com.example.slackbot.controller;

import com.example.slackbot.entity.Alert;
import com.example.slackbot.service.AlertService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class AlertFormController {

    private final AlertService alertService;

    // 🔥 생성자 주입 (AlertService를 의존성 주입받음)
    public AlertFormController(AlertService alertService) {
        this.alertService = alertService;
    }

    // 🔥 메인 화면: 등록된 알림 목록 보여주는 페이지
    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("alerts", alertService.getAllAlerts()); // DB에서 전체 알림 가져와서 모델에 담기
        return "form"; // templates/form.html 렌더링
    }

    // 🔥 알림 등록 처리
    @PostMapping("/add-alert")
    public String addAlert(@RequestParam String message,
                           @RequestParam String time) {
        LocalDateTime parsedTime = LocalDateTime.parse(time); // 문자열을 LocalDateTime으로 변환
        alertService.addAlert(message, parsedTime); // 알림 등록
        return "redirect:/"; // 등록 끝나면 메인으로 리다이렉트
    }

    // 🔥 알림 삭제 처리
    @GetMapping("/delete/{id}")
    public String deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id); // ID로 삭제
        return "redirect:/"; // 삭제 후 메인으로 이동
    }

    // 🔥 알림 수정 화면 보여주기
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Alert alert = alertService.getAlert(id).orElseThrow(); // ID로 알림 찾기, 없으면 에러

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); // HTML datetime-local 포맷
        String formattedTime = alert.getTime().format(formatter); // 알림 시간 포맷 변환

        model.addAttribute("alert", alert); // 수정할 알림 데이터
        model.addAttribute("formattedTime", formattedTime); // 포맷된 시간 따로 넘김

        return "edit-form"; // templates/edit-form.html 반환
    }

    // 🔥 알림 수정 저장 처리
    @PostMapping("/update/{id}")
    public String updateAlert(@PathVariable Long id,
                              @RequestParam String message,
                              @RequestParam String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); // 다시 포맷 파싱
        LocalDateTime parsedTime = LocalDateTime.parse(time, formatter); // 파싱해서 LocalDateTime으로 변환
        alertService.updateAlert(id, message, parsedTime); // 수정 저장
        return "redirect:/"; // 수정 후 메인으로 이동
    }

    @PostMapping("/api/update/{id}")
    @ResponseBody
    public String apiUpdateAlert(@PathVariable Long id,
                                 @RequestParam String message,
                                 @RequestParam String time) {
        try {
            // 이 포맷으로 꼭 맞춰줘야 함!
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime parsedTime = LocalDateTime.parse(time, formatter);
            alertService.updateAlert(id, message, parsedTime);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }


}
