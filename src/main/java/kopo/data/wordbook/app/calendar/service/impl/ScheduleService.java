package kopo.data.wordbook.app.calendar.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.calendar.controller.request.PostScheduleRequest;
import kopo.data.wordbook.app.calendar.controller.response.PostScheduleResponse;
import kopo.data.wordbook.app.calendar.exception.ScheduleException;
import kopo.data.wordbook.app.calendar.repository.ScheduleRepository;
import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntity;
import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntityId;
import kopo.data.wordbook.app.calendar.service.IScheduleService;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService implements IScheduleService {
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public PostScheduleResponse createSchedule(PostScheduleRequest body, HttpSession session) {
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);
        final String studentId = sessionInfo.studentId();

        StudentEntity student =
                studentRepository.findById(studentId).orElseThrow(
                        () -> new ScheduleException("student 가 존재하지 않음", HttpStatus.UNAUTHORIZED)
                );
        log.trace("student -> {}", student);

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

        PostScheduleResponse response = PostScheduleResponse.builder()
                .build();

        log.trace("response -> {}", response);
        return response;
    }
}
