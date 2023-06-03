package com.ricecha.s3.controller;

import org.springframework.web.bind.annotation.*;
import com.ricecha.s3.service.TranslationService;


public class TranslationController {
	
	private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    /**
     * 문서 번역 요청을 처리하는 엔드포인트
     *
     * @param filePath 번역할 문서의 파일 경로
     * @return 결과 메시지 ("Translation request submitted." 또는 "Translation failed: [에러 메시지]")
     */
    @PostMapping("translate/{filePath}")
    public String translateDocument(@PathVariable String filePath) {
        try {
            translationService.translateDocument(filePath);
            return "Translation request submitted.";
        } catch (Exception e) {
            return "Translation failed: " + e.getMessage();
        }
    }
}
