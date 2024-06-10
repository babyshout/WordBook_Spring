package kopo.data.wordbook.app.word.search.service;

import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;

public interface ISearchWordService {
    SearchWordResponse searchWord(String wordName, String studentId);

    String wordErrataCheck(String wordName);
}
