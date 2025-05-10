package com.example.controller.dto.response;

/**
 * @param allowed        разрешен ли запрос?
 * @param wordsCount     количество слов в переданном тексте
 * @param disallowReason причина запрета доступа к API
 */
public record TextAnalyzeResultResponse(
        boolean allowed,
        Integer wordsCount,
        String disallowReason
) {

    public static TextAnalyzeResultResponse apiError(String reason) {
        return new TextAnalyzeResultResponse(false, null, reason);
    }

    public static TextAnalyzeResultResponse success(int wordsCount){
        return new TextAnalyzeResultResponse(true, wordsCount, null);
    }
}
