package kopo.data.wordbook.app.famoussaying.service;

import kopo.data.wordbook.app.famoussaying.openfeign.KoreanAdviceOpenApiResponse;

public interface IFamoussayingService {
    /**
     * "korean-advice-open-api" 에서 한국어 명언을 가져온다
     *
     * @return {@link KoreanAdviceOpenApiResponse} 또는 null?
     */
    KoreanAdviceOpenApiResponse getRandomKoreanAdvice();
}
