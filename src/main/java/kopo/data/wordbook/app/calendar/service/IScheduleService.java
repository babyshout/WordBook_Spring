package kopo.data.wordbook.app.calendar.service;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.calendar.controller.request.PostScheduleRequest;
import kopo.data.wordbook.app.calendar.controller.response.PostScheduleResponse;

public interface IScheduleService {

    PostScheduleResponse createSchedule(PostScheduleRequest body, HttpSession session);
}
