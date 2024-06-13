package kopo.data.wordbook.app.word.rest.client.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchWordRestClientTest {

    @Autowired
    private SearchWordRestClient searchWordRestClient;

    @Test
    void searchNaverEncWord() {
        searchWordRestClient.searchNaverEncycWord("주식");
    }


    @Test
    void searchNaverErrataWord() {
        searchWordRestClient.searchNaverErrataWord("파라다");
        searchWordRestClient.searchNaverErrataWord("dkssud");
    }

    @Test
    void searchStdictWord() {


        searchWordRestClient.searchStdictWord("안녕-하다");

    }

    @Test
    void searchStdictWordList() {
//        searchWordRestClient.searchStdictWordList("안녕");
        searchWordRestClient.searchStdictWordList("주식");
    }
}