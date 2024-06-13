package kopo.data.wordbook.app.calendar.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.calendar.controller.request.PostScheduleRequest;
import kopo.data.wordbook.app.calendar.controller.response.ScheduleResponse;
import kopo.data.wordbook.app.calendar.exception.ScheduleException;
import kopo.data.wordbook.app.calendar.repository.ScheduleRepository;
import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntity;
import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntityId;
import kopo.data.wordbook.app.calendar.service.IScheduleService;
import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService implements IScheduleService {
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponse createSchedule(PostScheduleRequest body, HttpSession session) {

        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);
        final String studentId = sessionInfo.studentId();

        StudentEntity student = getStudent(studentId);


        ScheduleEntityId scheduleId = ScheduleEntityId.builder()
                .studentId(studentId)
                .scheduleSeq(body.id())
                .build();

        if (scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleException(
                    "이미 동일한 schedule 이 존재함",
                    HttpStatus.I_AM_A_TEAPOT
            );
        }

        ScheduleEntity build = ScheduleEntity.builder()
                .scheduleSeq(body.id())
                .studentId(student)
                .start(body.start())
                .end(body.end())
                .title(body.title())
                .build();


        ScheduleEntity saved = scheduleRepository.save(build);
        log.trace("saved ScheduleEntity -> {}", saved);

        ScheduleResponse response = ScheduleResponse.of(saved);

        log.trace("response -> {}", response);
        return response;
    }

    @Override
    public List<ScheduleResponse> getScheduleList(HttpSession session) {
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);


        StudentEntity student = getStudent(sessionInfo.studentId());

        List<ScheduleEntity> allByStudentId = scheduleRepository.findAllByStudentId(student);

        List<ScheduleResponse> responseList = new ArrayList<>();

        allByStudentId.forEach(schedule -> {
            responseList.add(ScheduleResponse.of(schedule));
        });

        // TODO 다른 것들도 추가해서 넣어줄것!
        List<NotepadEntity> notepadEntityList = student.getNotepadEntityList();
        notepadEntityList.forEach(notepadEntity -> {
            responseList.add(ScheduleResponse.of(notepadEntity));
        });

        return responseList;
    }

    @Override
    public Boolean deleteSchedule(Long scheduleSeq, HttpSession session) {
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        StudentEntity student = getStudent(sessionInfo.studentId());

        ScheduleEntityId id = ScheduleEntityId.builder()
                .scheduleSeq(scheduleSeq)
                .studentId(student.getStudentId())
                .build();
        log.trace("id to delete -> {}", id);
        log.trace(scheduleRepository.findById(id).toString());
        if (!scheduleRepository.existsById(id)) {
            log.warn("해당 ID 로 삭제요청 실패..!!!");
            throw new ScheduleException("해당 id 를 가진 schedule 이 없음!", HttpStatus.BAD_REQUEST);
        }

        scheduleRepository.deleteById(id);
        return true;
    }

    private StudentEntity getStudent(String studentId) {
        StudentEntity student =
                studentRepository.findById(studentId).orElseThrow(
                        () -> new ScheduleException("student 가 존재하지 않음", HttpStatus.UNAUTHORIZED)
                );
        log.trace("student -> {}", student);
        return student;
    }
}
