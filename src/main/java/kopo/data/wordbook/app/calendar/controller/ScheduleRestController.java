package kopo.data.wordbook.app.calendar.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.calendar.controller.request.PostScheduleRequest;
import kopo.data.wordbook.app.calendar.controller.response.ScheduleResponse;
import kopo.data.wordbook.app.calendar.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar/v1")
public class ScheduleRestController {

    private final IScheduleService calendarService;
    public static class MappedUrl {
        public static final String postSchedule = "/schedule";
        public static final String getScheduleList = "/scheduleList";
        public static final String deleteScheduleList = "/schedule";
    }

    @GetMapping(MappedUrl.getScheduleList)
    public ResponseEntity<List<ScheduleResponse>> getScheduleList(
            HttpSession session
    ) {
        List<ScheduleResponse> responseList = calendarService.getScheduleList(session);

        return ResponseEntity.ok(responseList);
    }

    @PostMapping(MappedUrl.postSchedule)
    public ResponseEntity<ScheduleResponse> postSchedule(
            HttpSession session,
            @RequestBody @Valid PostScheduleRequest body
    ) {
        log.trace("body -> {}", body);

        ScheduleResponse schedule = calendarService.createSchedule(body, session);


        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping(MappedUrl.deleteScheduleList)
    public ResponseEntity deleteSchedule(
            HttpSession session,
            @RequestParam("id") Long scheduleSeq
    ) {
        log.trace("request id -> {}", scheduleSeq);
        calendarService.deleteSchedule(scheduleSeq, session);

        return ResponseEntity.ok().build();
    }
}
