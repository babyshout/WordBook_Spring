package kopo.data.wordbook.app.word.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RestClientConfig {
    @Value("${naver.api.search.word.request_url.json}")
    private String requestUrlJson;
    @Value("${naver.api.search.word.X-Naver-Client-Id.value}")
    private String clientIdValue;
    @Value("${naver.api.search.word.X-Naver-Client-Id.header}")
    private String clientIdHeader;
    @Value("${naver.api.search.word.X-Naver-Client-Secret.value}")
    private String clientSecretValue;
    @Value("${naver.api.search.word.X-Naver-Client-Secret.header}")
    private String clientSecretHeader;
    @Bean("naverSearchRestClient")
    public RestClient naverSearchRestClient() {
        log.error("naverSearchRestClient @Bean !!!");
        log.trace("requestUrlJson -> {}", requestUrlJson);
        log.trace("clientIdValue -> {}", clientIdValue);
        log.trace("clientIdHeader -> {}", clientIdHeader);
        log.trace("clientSecretValue -> {}", clientSecretValue);
        log.trace("clientSecretHeader -> {}", clientSecretHeader);
//        RestTemplateBuilder

        RestClient naverSearchRestClient =
                RestClient.builder()
//                .baseUrl(requestUrlJson)
                        .defaultHeader(clientIdHeader, clientIdValue)
                        .defaultHeader(clientSecretHeader, clientSecretValue)
                        .build();

        log.trace("naverSearchRestClient -> {}", naverSearchRestClient);

        return naverSearchRestClient;
//        return RestClient.create();
    }


    @Value("${word.stdict.korean.go.kr.key}")
    private String stdictKey;
    @Value("${word.stdict.korean.go.kr.req_type.json}")
    private String stdictReqTypeJson;

    @Bean("stdictKoreanSearchRestClient")
    public RestClient stdictKoreanSearchRestClient() {

        RestClient stdictKoreanSearchRestClient =
                RestClient.builder()
                        .build();

        log.trace("stdictKoreanSearchRestClient -> {}", stdictKoreanSearchRestClient);

        return stdictKoreanSearchRestClient;

    }


}
