package kopo.data.wordbook.app.word.search.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.repository.MywordRepository;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import kopo.data.wordbook.app.word.rest.client.ISearchWordRestClient;
import kopo.data.wordbook.app.word.search.controller.response.RecentlySearchWord;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.controller.response.SimpleWordResponse;
import kopo.data.wordbook.app.word.search.service.ISearchWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchWordService implements ISearchWordService {

    private final StudentRepository studentRepository;
    private final ISearchWordRestClient searchWordRestClient;
    private final MywordRepository mywordRepository;

    @Override
    public SearchWordResponse searchWordDetail(String wordName, String studentId) {

        WordDocument wordDocument = searchWordRestClient.searchStdictWord(wordName);

        log.trace("wordDocument -> {}", wordDocument);

        // TODO 여기에서 최근 검색 단어에 넣어주기..


        return null;
    }

    @Override
    public List<SimpleWordResponse> searchSimpleWordList(String wordName) {
        return searchWordRestClient.searchStdictWordList(wordName);


//        return null;
    }

    @Override
    public String wordErrataCheck(String wordName) {

        String errataWord = searchWordRestClient.searchNaverErrataWord(wordName);
        log.trace("errataWord -> {}", errataWord);
        return errataWord;
    }

    @Override
    public List<RecentlySearchWord> getSearchRecentlySearchWord(String studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(() -> {
            throw new RuntimeException("123");
        });
        Optional<MywordEntity> byStudentAndMywordName = mywordRepository.findByStudentAndMywordName(
                student, MywordEntity.RECENTLY_SEARCH_MYWORD
        );

        if (byStudentAndMywordName.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> wordNameList = byStudentAndMywordName.get().getWordNameList();

        List<RecentlySearchWord> responseList = new ArrayList<>();
        wordNameList.forEach(s -> {
            responseList.add(RecentlySearchWord.builder()
                    .wordName(s)
                    .build());
        });


        log.trace("responseList -> {}", responseList);
        return responseList;
    }
}
