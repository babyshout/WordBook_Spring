package kopo.data.wordbook.app.word.search.service;

import kopo.data.wordbook.app.word.search.controller.response.RecentlySearchWord;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.controller.response.SimpleWordResponse;

import java.util.List;

public interface ISearchWordService {
    SearchWordResponse searchWordDetail(String wordName, String studentId);

    List<SimpleWordResponse> searchSimpleWordList(String wordName);

    String wordErrataCheck(String wordName);

    List<RecentlySearchWord> getSearchRecentlySearchWord(String studentId);
}
