package kopo.data.wordbook.app.word.rest.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import kopo.data.wordbook.app.word.repository.WordRepository;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import kopo.data.wordbook.app.word.rest.client.ISearchWordRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SearchWordRestClient implements ISearchWordRestClient {

    private final WordRepository wordRepository;


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
    public WordDocument searchStdictWord(String queryWord) {

        log.error("searchStdictWord -> stdictKoreanSearchRestClient -> {}", stdictKoreanSearchRestClient);

        String encodedQueryWord = URLEncoder.encode(queryWord, StandardCharsets.UTF_8);

//        new MongoTemplate()

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

        // 아래 중복된 wordName 할때.. 로그찍을라고 만듬 + 리턴할때 사용
        WordDocument saved = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            StdictKoreanSearchApiResponse apiResponse =
                    objectMapper.readValue(responseBodyToString, StdictKoreanSearchApiResponse.class);

            log.trace("apiResponse -> {}", apiResponse);

            WordDocument savingWord = WordDocument.of(apiResponse);
            log.trace("savingWord -> {}", savingWord);


            try {
                saved = wordRepository.save(savingWord);
            } catch (DuplicateKeyException e) {
                // log.method("String", Throwable) 하면.. String 출력하고, stacktrace 출력함
                log.warn("mongoDB insert 도중 DuplicateKeyException 발생!!", e);

//                WordDocument byWordName = Optional.ofNullable(
//                        wordRepository.findByWordName(savingWord.getWordName())
//                ).orElseThrow(()
//                        ->
//                        new RuntimeException("mongoDB insert 도중 중복되는 wordName 으로 인해 findByWordName 했지만, 찾지 못함"));
                WordDocument byWordName = wordRepository.findByWordName(
                                savingWord.getWordName()
                        ).orElseThrow(()
                                ->
                                new RuntimeException("mongoDB insert 도중 중복되는 wordName 으로 인해 findByWordName 했지만, 찾지 못함"));
//                ).orElseThrow(()
//                        ->
//                        new DuplicateKeyException("mongoDB insert 도중 중복되는 wordName 으로 인해 findByWordName 했지만, 찾지 못함"));
//                log.warn(e.st);

                List<WordDocument.WordDetail> wordDetails = WordDocument.WordDetail.of(apiResponse);
                byWordName.setWordDetailList(wordDetails);

                saved = wordRepository.save(byWordName);
            } finally {
                log.trace("saved by duplicated wordName -> {}", saved);
            }
        } catch (JsonProcessingException e) {
            log.warn("Json parsing 중 예외 발생!!!", e);
            throw new RuntimeException(e);
        }


        // 위에서 Exception 생기면.. 애초에 여기까지 오지도 못함
        return saved;
    }
}
