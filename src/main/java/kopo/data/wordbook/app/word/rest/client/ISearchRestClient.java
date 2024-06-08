package kopo.data.wordbook.app.word.rest.client;

public interface ISearchRestClient {
    void searchNaverEncWord(String queryWord);

    void searchStdictWord(String queryWord);
}
