package kopo.data.wordbook.app.word.rest.client.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchRestClientTest {

    @Autowired
    private SearchRestClient searchRestClient;

    @Test
    void searchNaverEncWord() {
        searchRestClient.searchNaverEncWord("주식");
    }


    @Test
    void searchStdictWord() {


        searchRestClient.searchStdictWord("주식");

    }
}