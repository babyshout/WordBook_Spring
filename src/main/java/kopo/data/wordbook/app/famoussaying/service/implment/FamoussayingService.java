package kopo.data.wordbook.app.famoussaying.service.implment;

import kopo.data.wordbook.app.famoussaying.openfeign.KoreanAdviceOpenApiOpenFeign;
import kopo.data.wordbook.app.famoussaying.openfeign.KoreanAdviceOpenApiResponse;
import kopo.data.wordbook.app.famoussaying.service.IFamoussayingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FamoussayingService implements IFamoussayingService {
    private final KoreanAdviceOpenApiOpenFeign koreanAdviceOpenApiOpenFeign;
    /**
     * "korean-advice-open-api" 에서 한국어 명언을 가져온다
     *
     * @return {@link KoreanAdviceOpenApiResponse} 또는 null?
     */
    @Override
    public KoreanAdviceOpenApiResponse getRandomKoreanAdvice() {
        return koreanAdviceOpenApiOpenFeign.get();
    }
}
