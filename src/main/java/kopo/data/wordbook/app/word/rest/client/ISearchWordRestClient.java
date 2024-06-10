package kopo.data.wordbook.app.word.rest.client;

import kopo.data.wordbook.app.word.repository.document.WordDocument;

public interface ISearchWordRestClient {
    void searchNaverEncycWord(String queryWord);

    String searchNaverErrataWord(String queryWord);

    WordDocument searchStdictWord(String queryWord);
}
