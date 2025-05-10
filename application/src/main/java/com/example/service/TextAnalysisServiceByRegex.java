package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class TextAnalysisServiceByRegex implements TextAnalysisService {
    public int countWords(String text) {
        return (int) java.util.Arrays.stream(text.trim().split("\\s+"))
                .filter(s -> !s.isEmpty())
                .count();
    }
}
