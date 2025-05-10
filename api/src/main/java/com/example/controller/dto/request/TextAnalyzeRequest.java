package com.example.controller.dto.request;

/**
 * Тело запроса на передачу текста для обработки
 *
 * @param text текст для обработки
 */
public record TextAnalyzeRequest(
        String text
) {
}
