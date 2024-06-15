package kopo.data.wordbook.app.word.myword.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.controller.request.PostWordNameToMywordRequest;
import kopo.data.wordbook.app.word.myword.controller.response.MywordDetailResponse;
import kopo.data.wordbook.app.word.myword.controller.response.MywordResponse;
import kopo.data.wordbook.app.word.myword.controller.response.SimpleMywordResponse;
import kopo.data.wordbook.app.word.myword.repository.MywordRepository;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntityId;
import kopo.data.wordbook.app.word.myword.service.MywordService;
import kopo.data.wordbook.app.word.repository.WordRepository;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
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
public class MywordServiceImpl implements MywordService {

    private final MywordRepository mywordRepository;
    private final StudentRepository studentRepository;
    private final WordRepository wordRepository;


    /**
     * studentId 로 MywordEntity List 를 조회하고, SimpleMywordResponse 로 바꿔서 리턴함!!
     *
     * @param studentId 조회할 학생 아이디
     * @return List of {@link SimpleMywordResponse}
     */
    @Transactional
    @Override
    public List<SimpleMywordResponse> getSimpleMywordList(String studentId) {

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("MywordEntity 조회중, studentId 와 일치하는 studentEntity 가 없음!!"));

        log.trace("student -> {}", student);

        List<MywordEntity> allByStudentIs = mywordRepository.findAllByStudentIs(student);

        log.trace("allByStudentIs -> {}", allByStudentIs);

        List<SimpleMywordResponse> responseList = new ArrayList<>();
        allByStudentIs.forEach(mywordEntity ->
                responseList.add(new SimpleMywordResponse(mywordEntity.getMywordName())));

        log.trace("responseList -> {}", responseList);

        return responseList;
    }

    /**
     * 제공받은 아이디에 새로운 Myword 를 추가함
     *
     * @param newMywordName 추가할 단어장의 이름
     * @param studentId     추가할 사용자 아이디
     * @return 모든과정이 성공적으로 끝나면, SimpleMywordResponse 를 리턴함..
     */
    @Transactional
    @Override
    public List<SimpleMywordResponse> addNewMyword(String newMywordName, String studentId) {

        // 새로운 MywordEntity 생성에 사용할 사용자를 가져옴
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("MywordEntity 조회중, studentId 와 일치하는 studentEntity 가 없음!!"));

        log.trace("student -> {}", student);

        // 이미 존재한다면.. throw 함
        if (mywordRepository.existsById(MywordEntityId.builder()
                .mywordName(newMywordName)
                .student(studentId)
                .build()
        )) {
            log.warn("이미 존재하는 myword 를 추가하려고 함!!");
            throw new RuntimeException("이미 존재하는 myword 를 추가하려고 함!!");
        }

        // 생성후, 저장!
        MywordEntity saving = MywordEntity.builder()
                .mywordName(newMywordName)
                .student(student)
                .build();
        log.trace("before saving MywordEntity -> {}", saving);
        MywordEntity saved = mywordRepository.save(saving);

        log.trace("saved MywordEntity -> {}", saved);

        // FIXME 여기서부터 조금 비효율적인 로직.. 리펙터링 필요할수 있음
        return this.getSimpleMywordList(studentId);
    }

    /**
     * body 에서 추가할 단어이름, 단어장이름 추출해서 studentId 에 넣어줌
     *
     * @param body      {@link PostWordNameToMywordRequest} 추가할 단어 이름, 단어장 이름이 들어있음
     * @param studentId 추가할 사용자 아이디
     * @return 추가된 결과를 가져온 MywordEntity 를 MywordResponse 로 바꿔서 리턴함
     */
    @Override
    @Transactional
    public MywordResponse postWordNameToMyword(PostWordNameToMywordRequest body, String studentId) {
        MywordEntity entity = this.addWordNameToMywordOfStudentId(body.wordName(), body.mywordName(), studentId);

        MywordResponse response = MywordResponse.of(entity);

        log.trace("response -> {}", response);

        return response;
    }

    /**
     * wordName 을 studentId 의 mywordName 에 추가함!
     *
     * @param wordName
     * @param mywordName
     * @param studentId
     * @return 추가된 {@link MywordEntity}
     */
    @Override
    public MywordEntity addWordNameToMywordOfStudentId(String wordName, String mywordName, String studentId) {

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("MywordEntity 조회중, studentId 와 일치하는 studentEntity 가 없음!!"));

        // 추가하고싶다면.. 해당 단어가 mongoDB 에 존재해야함!!
        if (!WordDocument.exists(wordName, wordRepository)) {
            log.warn("해당 " + wordName + " 가 mongoDB 에 없음..!");
            throw new RuntimeException("해당 " + wordName + " 가 mongoDB 에 없음..!");
        }

        MywordEntity mywordEntity = mywordRepository.findByStudentAndMywordName(student, mywordName)
                .orElseGet(() -> {
                    log.trace("{} 를 가진 단어장이 없어 새로 생성!", mywordName);
                    return mywordRepository.save(MywordEntity.builder()
                            .mywordName(mywordName)
                            .student(student)
                            .build()
                    );
                });
        log.trace("mywordEntity before add wordName -> {}", mywordEntity);

        List<String> wordNameList = mywordEntity.addWordNameToWordNameList(wordName, wordRepository);
        log.trace("wordNameList -> {}", wordNameList);

        log.trace("mywordEntity after add {} -> {}", wordName, mywordEntity);
        return mywordEntity;
    }

    /**
     * studentId 로 전체 단어장을 검색해서 {@link List<MywordResponse>} 로 바꿔서 리턴함
     *
     * @param studentId 검색할 사용자 아이디
     * @return {@link List<MywordResponse>}
     */
    @Transactional
    @Override
    public List<MywordResponse> getMywordResponseList(String studentId) {

        // 학생 조회
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("MywordEntity 조회중, studentId 와 일치하는 studentEntity 가 없음!!"));
        log.trace("student -> {}", student);

        // 해당 학생으로 전체 단어장 조회
        List<MywordEntity> allByStudentIs = mywordRepository.findAllByStudentIs(student);
        log.trace("allByStudnetIs -> {}", allByStudentIs);

        // List<MywordResponse> 에 담아줌..
        List<MywordResponse> responseList = new ArrayList<>();

        allByStudentIs.forEach(entity -> {
            responseList.add(MywordResponse.of(entity));
        });

        log.trace("responseList -> {}", responseList);

        return responseList;
    }

    /**
     * studentId 와 mywordName 으로 특정 MywordEntity 의 wordNameList 에서 wordDocument 를 가져옴!
     *
     * @param mywordName 단어장 이름
     * @param studentId  검색할 사용자 아이디
     * @return {@link WordDocument} 가 들어있는
     * {@link List} 를 갖고있는 MywordDetailResopnse 를 리턴함!!!
     */
    @Override
    public MywordDetailResponse getMywordDetailResponseList(String mywordName, String studentId) {
        // 학생 조회
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("MywordEntity 조회중, studentId 와 일치하는 studentEntity 가 없음!!"));
        log.trace("student -> {}", student);

        // 해당 학생으로 단어장 조회
        MywordEntity myword = mywordRepository.findByStudentAndMywordName(student, mywordName)
                .orElseThrow(() -> new RuntimeException("해당 조건으로 찾을수 있는 MywordEntity 가 없음!"));
        log.trace("myword -> {}", myword);

        List<String> wordNameList = myword.getWordNameList();

        // FIXME 리펙터링 가능할것으로 보임
        List<WordDocument> wordDocumentList = this.getWordDocumentList(wordNameList);


        MywordDetailResponse detailResponse = MywordDetailResponse.of(mywordName, wordDocumentList);
        log.trace("detailResponse -> {}", detailResponse);

        return detailResponse;
    }

    /**
     * FIXME 리펙터링 가능할것으로 보임..
     *
     * @param wordNameList 검색할 단어이름이 들어있는 {@link List}
     * @return {@link WordDocument} 가 들어있는 {@link List}
     */
    @Override
    public List<WordDocument> getWordDocumentList(List<String> wordNameList) {
        List<WordDocument> wordDocumentList = new ArrayList<>();

        wordNameList.forEach(wordName -> {
            Optional<WordDocument> optional = wordRepository.findByWordName(wordName);
            if(optional.isEmpty()){
                log.error("wordRepository.findByWordName({}) 실패!!!! 실패하면 안됨!!!!!", wordName);
                return;
            }
            wordDocumentList.add(optional.get());
        });

        log.trace("wordDocumentList -> {}", wordDocumentList);
        return wordDocumentList;
    }

}
