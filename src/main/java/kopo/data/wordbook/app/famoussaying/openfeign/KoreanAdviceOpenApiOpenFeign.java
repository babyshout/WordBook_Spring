package kopo.data.wordbook.app.famoussaying.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// TODO url > application.properties 로 이동
@FeignClient(name = "KoreanAdviceOpenApiOpenFeign", url = "https://korean-advice-open-api.vercel.app/api/advice")
public interface KoreanAdviceOpenApiOpenFeign {
    @GetMapping
    KoreanAdviceOpenApiResponse get();
}
