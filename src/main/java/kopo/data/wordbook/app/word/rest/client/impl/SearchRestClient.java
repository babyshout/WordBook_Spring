package kopo.data.wordbook.app.word.rest.client.impl;

import jakarta.annotation.Resource;
import kopo.data.wordbook.app.word.rest.client.ISearchRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SearchRestClient implements ISearchRestClient {


    @Value("${naver.api.search.word.request_url.json}")
    private String naverSearchRequestUrlJson;
    @Resource
//            (name = "naverSearchRestClient")
    private final RestClient naverSearchRestClient;


    @Override
    public void searchNaverEncWord(String queryWord) {

        log.error("searchWord -> naverSearchRestClient -> {}", naverSearchRestClient);

//        log.error(naverSearchRestClient.)

        String encodedQueryWord = URLEncoder.encode(queryWord, StandardCharsets.UTF_8);

        log.trace("queryWord -> {}", queryWord);
        log.error("encodedQueryWord -> {}", encodedQueryWord);

        String uriToQuery =
                naverSearchRequestUrlJson +
                        "?query=" + encodedQueryWord
//                                        +
//                                        "&display=100"
                ;
        log.trace("uriToQuery -> {}", uriToQuery);

        ResponseEntity<String> entity =
                naverSearchRestClient.get()
                        .uri(uriToQuery)
                        .retrieve().toEntity(String.class);
// ResponseEntity<String> entity =
//                naverSearchRestClient.get().retrieve().toEntity(String.class);

        log.error(entity.getBody().toString());

//        RestTemplate = RestTemplateBuilder
//        RestClient.builder().
//        RestClientBuilderConfigurer
//        ElasticsearchProperties.Restclient
//        RestClientCustomizer
//        RestClient
    }


    @Value("${word.stdict.korean.go.kr.key}")
    private String stdictKey;
    @Value("${word.stdict.korean.go.kr.req_type.json}")
    private String stdictReqTypeJson;
    @Value("${word.stdict.korean.go.kr.request_url}")
    private String stdictRequestUrl;
    @Resource
//            (name = "stdictKoreanSearchRestClient")
    private final RestClient stdictKoreanSearchRestClient;

    @Override
    public void searchStdictWord(String queryWord) {

        log.error("searchStdictWord -> stdictKoreanSearchRestClient -> {}", stdictKoreanSearchRestClient);

        String encodedQueryWord = URLEncoder.encode(queryWord, StandardCharsets.UTF_8);

        log.trace("queryWord -> {}", queryWord);
        log.trace("encodedQueryWord -> {}", encodedQueryWord);

        log.trace("stdictKoreanSearchRestClient.get().toString() -> {}",
                stdictKoreanSearchRestClient.get().toString());

        String uriToQuery =
                stdictRequestUrl +
                        "?" + stdictKey +
                        "&q=" + encodedQueryWord +
                        "&" + stdictReqTypeJson
//                                        +
//                                        "&display=100"
                ;
        log.trace("uriToQuery -> {}", uriToQuery);

        ResponseEntity<String> entity =
                stdictKoreanSearchRestClient.get()
                        .uri(uriToQuery)
                        .retrieve().toEntity(String.class);

        log.trace("entity.getBody().toString() -> {}",
                entity.getBody().toString());

        String responseBody = entity.getBody().toString();
//        Gson
    }
}
