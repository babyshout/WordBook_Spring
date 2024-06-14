package kopo.data.wordbook.app.word.search.service;

import kopo.data.wordbook.app.word.repository.document.WordDocument;
import kopo.data.wordbook.app.word.search.controller.response.RecentlySearchWord;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.controller.response.SimpleWordResponse;

import java.util.List;

public interface ISearchWordService {
    SearchWordResponse searchWordDetail(String wordName, String studentId);

    List<SimpleWordResponse> searchSimpleWordList(String wordName);

    String wordErrataCheck(String wordName);

    List<RecentlySearchWord> getSearchRecentlySearchWord(String studentId);

    /**
     * 단순히, wordDocument 에서 find first 해서 가져옴..
     * @return
     */
    WordDocument getTodaySearchWord();
}
