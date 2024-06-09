package kopo.data.wordbook.app.word.search.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
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



        return null;
    }
}
