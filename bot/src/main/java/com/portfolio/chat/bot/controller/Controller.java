package com.portfolio.chat.bot.controller;

import com.portfolio.chat.bot.serviceImpl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/gemini/api/v1")
public class Controller {
    private final ServiceImpl serviceImpl;
    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> payload){
        String question= payload.get("question");
        String answer= serviceImpl.getAnswer(question);
        return ResponseEntity.ok(answer);
    }
}
