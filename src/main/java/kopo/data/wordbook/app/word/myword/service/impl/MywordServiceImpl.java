package kopo.data.wordbook.app.word.myword.service.impl;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.controller.response.SimpleMywordResponse;
import kopo.data.wordbook.app.word.myword.repository.MywordRepository;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.myword.service.MywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
