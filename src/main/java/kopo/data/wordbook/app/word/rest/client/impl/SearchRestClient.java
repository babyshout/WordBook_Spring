package kopo.data.wordbook.app.word.rest.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import kopo.data.wordbook.app.word.rest.client.ISearchRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

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
                        "&" + stdictReqTypeJson;
        log.trace("uriToQuery -> {}", uriToQuery);

//        UriComponents = UriComponentsBuilder.fromHttpUrl(stdictRequestUrl)

        ResponseEntity<String> entity =
                stdictKoreanSearchRestClient.get()
                        .uri(uriToQuery)
                        .retrieve().toEntity(String.class);

        log.trace("entity.getBody().toString() -> {}",
                entity.getBody().toString());


        RestTemplate restTemplate = new RestTemplate();


        String responseBodyToString = entity.getBody().toString();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            StdictKoreanSearchApiResponse apiResponse =
                    objectMapper.readValue(responseBodyToString, StdictKoreanSearchApiResponse.class);

            log.trace("apiResponse -> {}", apiResponse);
        } catch (JsonProcessingException e) {
            log.warn("Json parsing 중 예외 발생!!!");
            throw new RuntimeException(e);
        }


//        JSONParser jsonParser = new JSONParser(responseBodyToString);
//        try {
//            LinkedHashMap<String, Object> channel = jsonParser.parseObject();
//            log.trace("channel -> {}", channel);
//            log.trace("channel.get(\"channel\") -> {}", channel.get("channel"));
//
//
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
    }
}
