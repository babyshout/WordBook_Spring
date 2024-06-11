package kopo.data.wordbook.app.word.search.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import kopo.data.wordbook.app.word.rest.client.ISearchWordRestClient;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.service.ISearchWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchWordService implements ISearchWordService {

    private final StudentRepository studentRepository;
    private final ISearchWordRestClient searchWordRestClient;

    @Override
    public SearchWordResponse searchWord(String wordName, String studentId) {

        WordDocument wordDocument = searchWordRestClient.searchStdictWord(wordName);


        return null;
    }

    @Override
    public String wordErrataCheck(String wordName) {

        String errataWord = searchWordRestClient.searchNaverErrataWord(wordName);
        log.trace("errataWord -> {}", errataWord);
        return errataWord;
    }
}
