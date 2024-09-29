package kopo.data.wordbook.app.famoussaying.controller;

import kopo.data.wordbook.app.famoussaying.openfeign.KoreanAdviceOpenApiResponse;
import kopo.data.wordbook.app.famoussaying.service.IFamoussayingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/famoussaying/v1")
@RequiredArgsConstructor
public class FamoussayingRestController {

    private final IFamoussayingService famoussayingService;

    /**
     * 이 메서드에서 핸들링하는 url 을 관리
     */
    public static class HandleUrl {
        public static final String getKoreanAdviceOpenApi =
                "/korean-advice-open-api";
    }
    @GetMapping(HandleUrl.getKoreanAdviceOpenApi)
    ResponseEntity<KoreanAdviceOpenApiResponse> getKoreanAdviceOpenApi() {
        return ResponseEntity.ok(famoussayingService.getRandomKoreanAdvice());
    }
}
