package kopo.data.wordbook.app.word.myword.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.controller.response.SimpleMywordResponse;
import kopo.data.wordbook.app.word.myword.repository.MywordRepository;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntityId;
import kopo.data.wordbook.app.word.myword.service.MywordService;
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

}
