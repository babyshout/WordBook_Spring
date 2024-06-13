package kopo.data.wordbook.app.calendar.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PostScheduleRequest(
        @NotNull
        String title,
        @NotNull
        @JsonFormat
        LocalDate start,
        @NotNull
        LocalDate end,
        @AssertTrue
        Boolean allDay,
        @NotNull
        Long id

) {
    @AssertTrue
    public Boolean startBeforeEnd() {

        return this.start.isBefore(end);
    }
}
