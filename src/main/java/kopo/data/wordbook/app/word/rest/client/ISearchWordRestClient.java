package kopo.data.wordbook.app.word.rest.client;

import kopo.data.wordbook.app.word.repository.document.WordDocument;
import kopo.data.wordbook.app.word.search.controller.response.SimpleWordResponse;

import java.util.List;

public interface ISearchWordRestClient {
    void searchNaverEncycWord(String queryWord);

    String searchNaverErrataWord(String queryWord);

    WordDocument searchStdictWord(String queryWord);

    List<SimpleWordResponse> searchStdictWordList(String queryWord);
}
