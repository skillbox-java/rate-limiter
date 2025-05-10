package com.example.controller;

import com.example.ClientApp;
import com.example.RateLimitDecision;
import com.example.controller.dto.request.RegistrationUserRequest;
import com.example.controller.dto.request.TextAnalyzeRequest;
import com.example.controller.dto.response.RegistrationResponse;
import com.example.controller.dto.response.TextAnalyzeResultResponse;
import com.example.service.ClientRegistrationService;
import com.example.service.RateLimiterService;
import com.example.service.TextAnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WordCountController {

    private final RateLimiterService rateLimiterService;
    private final ClientRegistrationService registrationService;
    private final TextAnalysisService textAnalysisService;

    public WordCountController(RateLimiterService rateLimiterService,
                               ClientRegistrationService registrationService,
                               TextAnalysisService textAnalysisService) {
        this.rateLimiterService = rateLimiterService;
        this.registrationService = registrationService;
        this.textAnalysisService = textAnalysisService;
    }

    @PostMapping("/words")
    public ResponseEntity<TextAnalyzeResultResponse> countWords(@RequestHeader("X-API-Key") String apiKey,
                                                                @RequestBody TextAnalyzeRequest body) {
        RateLimitDecision decision = rateLimiterService.checkLimit(apiKey);

        if (!decision.allowed()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(TextAnalyzeResultResponse.apiError(decision.reason()));
        }

        rateLimiterService.logRequest(apiKey);

        String text = Optional.ofNullable(body.text()).orElseThrow();

        int wordCount = textAnalysisService.countWords(text);

        return ResponseEntity.ok(TextAnalyzeResultResponse.success(wordCount));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerClient(@RequestBody RegistrationUserRequest body) {
        String name = Optional.ofNullable(body.name())
                .orElseThrow();

        ClientApp client = registrationService.register(name);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegistrationResponse(client.name(), client.apiKey()));
    }
}

