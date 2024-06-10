package kopo.data.wordbook.app.word.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class RestClientConfig {
    @Value("${naver.api.search.word.request_url.json}")
    private String naverEncycRequestUrlJson;
    @Value("${naver.api.X-Naver-Client-Id.value}")
    private String clientIdValue;
    @Value("${naver.api.X-Naver-Client-Id.header}")
    private String clientIdHeader;
    @Value("${naver.api.X-Naver-Client-Secret.value}")
    private String clientSecretValue;
    @Value("${naver.api.X-Naver-Client-Secret.header}")
    private String clientSecretHeader;
    @Bean("naverSearchEncycRestClient")
    public RestClient naverSearchEncycRestClient() {
        log.error("naverSearchEncycRestClient @Bean !!!");
        log.trace("naverEncycRequestUrlJson -> {}", naverEncycRequestUrlJson);
        log.trace("clientIdValue -> {}", clientIdValue);
        log.trace("clientIdHeader -> {}", clientIdHeader);
        log.trace("clientSecretValue -> {}", clientSecretValue);
        log.trace("clientSecretHeader -> {}", clientSecretHeader);
//        RestTemplateBuilder

        RestClient naverSearchEncycRestClient =
                RestClient.builder()
//                .baseUrl(naverEncycRequestUrlJson)
                        .defaultHeader(clientIdHeader, clientIdValue)
                        .defaultHeader(clientSecretHeader, clientSecretValue)
                        .build();

        log.trace("naverSearchEncycRestClient -> {}", naverSearchEncycRestClient);

        return naverSearchEncycRestClient;
//        return RestClient.create();
    }


    @Value("${naver.api.search.errata.request_url.json}")
    private String naverErrataRequestUrlJson;

    @Bean("naverSearchErrataRestClient")
    public RestClient naverSearchErrataRestClient() {
        log.error("naverSearchErrataRestClient @Bean !!!");
        log.trace("naverErrataRequestUrlJson -> {}", naverErrataRequestUrlJson);
        log.trace("clientIdValue -> {}", clientIdValue);
        log.trace("clientIdHeader -> {}", clientIdHeader);
        log.trace("clientSecretValue -> {}", clientSecretValue);
        log.trace("clientSecretHeader -> {}", clientSecretHeader);
//        RestTemplateBuilder

        RestClient naverSearchErrataRestClient =
                RestClient.builder()
//                .baseUrl(naverEncycRequestUrlJson)
                        .defaultHeader(clientIdHeader, clientIdValue)
                        .defaultHeader(clientSecretHeader, clientSecretValue)
                        .build();

        log.trace("naverSearchErrataRestClient -> {}", naverSearchErrataRestClient);

        return naverSearchErrataRestClient;
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
