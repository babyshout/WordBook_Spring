package kopo.data.wordbook.app.calendar.service;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.calendar.controller.request.PostScheduleRequest;
import kopo.data.wordbook.app.calendar.controller.response.ScheduleResponse;

import java.util.List;

public interface IScheduleService {

    ScheduleResponse createSchedule(PostScheduleRequest body, HttpSession session);

    List<ScheduleResponse> getScheduleList(HttpSession session);

    Boolean deleteSchedule(Long scheduleSeq, HttpSession session);
}
