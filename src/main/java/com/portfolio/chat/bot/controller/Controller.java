package com.portfolio.chat.bot.controller;

import com.portfolio.chat.bot.serviceImpl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
public class Controller {
    private final ServiceImpl serviceImpl;

    @GetMapping("/")
    public String defaultCase(){
        return "Server is running";
    }
    @CrossOrigin(origins = {"http://localhost:3001", "https://my-portfolio-8b9j.onrender.com/"})
    @PostMapping("/gemini/api/v1/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> payload){
        String question= payload.get("question");
        String answer= serviceImpl.getAnswer(question);
        return ResponseEntity.ok(answer);
    }
}
