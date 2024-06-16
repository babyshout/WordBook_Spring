package kopo.data.wordbook.app.dashboard.service.impl;

import kopo.data.wordbook.app.dashboard.service.DashboardService;
import kopo.data.wordbook.app.notepad.controller.reponse.GetNotepadResponse;
import kopo.data.wordbook.app.notepad.repository.NotepadRepository;
import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final NotepadRepository notepadRepository;
    private final StudentRepository studentRepository;

    /**
     * studentId 의 notepad 를 amount 만큼 최근순으로 가져옴
     *
     * @param studentId 조회시 사용할 id
     * @param amount    가져올 양
     * @return 최근순으로 가져옴..
     */
    @Override
    public List<GetNotepadResponse> getRecentlyNotepadList(String studentId, Long amount) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Dashboard 에서 사용할 notepad 조회중, 동일한 student Entity 가 없음!!"));
        log.trace("student -> {}", student);

        List<NotepadEntity> allByStudentOrderByChgDateDesc =
                notepadRepository.findAllByRegStudentOrderByChgDateDesc(student);
        log.trace("allByStudentOrderByChgDateDesc -> {}", allByStudentOrderByChgDateDesc);

        List<GetNotepadResponse> responseList = new ArrayList<>();

        allByStudentOrderByChgDateDesc.stream().limit(amount)
                .forEach(notepadEntity -> {
                    responseList.add(GetNotepadResponse.of(notepadEntity));
                });

        log.trace("responseList -> {}", responseList);

        return responseList;
    }
}
