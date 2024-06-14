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
     *
     * @return findFirstBy 로 나온 WordDocument
     */
    WordDocument getTodaySearchWord();

    /**
     * 단어검색하면 호출할 메서드.. 검색과 동시에 사용자 최근 검색단어에 추가함!!
     *
     * @param wordName 검색할 단어 이름
     * @param studentId 최근검색목록에 추가할 student's ID
     * @return 검색까지 다 끝나면 해당 wordName 으로 검색한 WordDocument 를 리턴함
     */
    WordDocument getSearchWordDetail(String wordName, String studentId);
}
