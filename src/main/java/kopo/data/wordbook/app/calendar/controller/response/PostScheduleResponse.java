package kopo.data.wordbook.app.calendar.controller.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PostScheduleResponse(
        String title,
        LocalDate start,
        LocalDate end
) {
}
