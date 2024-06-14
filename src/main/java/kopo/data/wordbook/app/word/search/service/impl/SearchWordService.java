package kopo.data.wordbook.app.word.search.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.repository.MywordRepository;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.repository.WordRepository;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import kopo.data.wordbook.app.word.rest.client.ISearchWordRestClient;
import kopo.data.wordbook.app.word.search.controller.response.RecentlySearchWord;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.controller.response.SimpleWordResponse;
import kopo.data.wordbook.app.word.search.service.ISearchWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final WordRepository wordRepository;

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
        StudentEntity student =
                studentRepository.findById(studentId)
                        .orElseThrow(() -> new RuntimeException("최근 검색 목록 불러오는 도중 동일한 student 가 없어 오류 발생"));
        Optional<MywordEntity> byStudentAndMywordName = mywordRepository.findByStudentAndMywordName(
                student, MywordEntity.RECENTLY_SEARCH_MYWORD
        );

        if (byStudentAndMywordName.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> wordNameList = byStudentAndMywordName.get().getWordNameList();

        List<RecentlySearchWord> responseList = new ArrayList<>();
        wordNameList.forEach(
                s -> responseList.add(RecentlySearchWord.builder()
                        .wordName(s)
                        .build())
        );


        log.trace("responseList -> {}", responseList);
        return responseList;
    }

    /**
     * 단순히, wordDocument 에서 find first 해서 가져옴..
     *
     * @return findFirstBy 로 나온 WordDocument
     */
    @Override
    public WordDocument getTodaySearchWord() {
        WordDocument wordDocument = wordRepository.findFirstBy()
                .orElseThrow(() -> new RuntimeException("WordDocument 가 없음!!!"));

        log.trace("wordDocument By getTodaySearchWord() -> {}", wordDocument);
        return wordDocument;
    }

    /**
     * 단어검색하면 호출할 메서드.. 검색과 동시에 사용자 최근 검색단어에 추가함!!
     *
     * @param wordName 검색할 단어 이름
     * @param studentId 최근검색목록에 추가할 student's ID
     * @return 검색까지 다 끝나면 해당 wordName 으로 검색한 WordDocument 를 리턴함
     */
    @Transactional
    @Override
    public WordDocument getSearchWordDetail(String wordName, String studentId) {
        WordDocument wordDocument = searchWordRestClient.searchStdictWord(wordName);

        log.trace("wordDocument -> {}", wordDocument);

        // 최근검색단어에 넣어주는 과정 시작
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(() ->
                new RuntimeException("단어검색 시도했으나 동일한 사용자가 없음!!"));

        MywordEntity mywordEntity = mywordRepository.findByStudentAndMywordName(student, MywordEntity.RECENTLY_SEARCH_MYWORD)
                // 최근 검색 단어장을 불러오고, 없다면 새로 생성!!!
                .orElseGet(() -> {
                    MywordEntity buildInOrElseGet = MywordEntity.builder().mywordName(MywordEntity.RECENTLY_SEARCH_MYWORD)
                            .student(student)
                            .build();
                    log.trace("buildInOrElseGet -> {}", buildInOrElseGet);
                    return mywordRepository.save(buildInOrElseGet);
                });

        log.trace("mywordEntity -> {}", mywordEntity);

        List<String> wordNameList = mywordEntity.addWordNameToWordNameList(wordName, wordRepository);
        log.trace("wordNameList in mywordEntity -> {}", wordNameList);
        // 최근검색단어에 넣어주는 과정 끝

        return wordDocument;
    }
}
