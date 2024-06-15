package kopo.data.wordbook.app.word.myword.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.controller.request.PostWordNameToMywordRequest;
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

}
