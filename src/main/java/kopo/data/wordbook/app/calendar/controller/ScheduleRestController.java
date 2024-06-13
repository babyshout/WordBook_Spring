package kopo.data.wordbook.app.calendar.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.calendar.controller.request.PostScheduleRequest;
import kopo.data.wordbook.app.calendar.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar/v1")
public class ScheduleRestController {

    private final IScheduleService calendarService;
    public static class MappedUrl {
        public static final String postSchedule = "/schedule";
    }

    @PostMapping(MappedUrl.postSchedule)
    public ResponseEntity postSchedule(
            HttpSession session,
            @RequestBody @Valid PostScheduleRequest body
    ) {
        log.trace("body -> {}", body);

        calendarService.createSchedule(body, session);



        return null;
    }
}
