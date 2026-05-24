package ru.muwa.catlog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.muwa.catlog.service.RagService;

import java.util.Map;

@Controller
@RequestMapping("/advice")
@RequiredArgsConstructor
public class CatAdviceController {

    private final RagService ragService;

    @GetMapping
    public ResponseEntity<Map<String, String>> askForVerdict(){

        System.out.println("Вызван метод совета ИИ");
        var advice = ragService.getAdvice();
        System.out.println("Получен ответ от ИИ: ");
        System.out.println(advice);
        return ResponseEntity.ok(Map.of("advice" , advice ))  ;

    }

}
