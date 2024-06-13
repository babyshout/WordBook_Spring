package kopo.data.wordbook.app.calendar.controller.response;

import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntity;
import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ScheduleResponse(
        Long id,
        String title,
        LocalDate start,
        LocalDate end,
        Boolean isDeletable
) {
    /**
     * schedule Entity 로 만들어진 events 는 삭제가 가능해야 함!!
     * @param scheduleEntity
     * @return
     */
    public static ScheduleResponse of(ScheduleEntity scheduleEntity) {
        return ScheduleResponse.builder()
                .id(scheduleEntity.getScheduleSeq())
                .isDeletable(true)
                .start(scheduleEntity.getStart())
                .end(scheduleEntity.getEnd())
                .title(scheduleEntity.getTitle())
                .build();
    }

    public static ScheduleResponse of(NotepadEntity notepadEntity) {
        String title = "공부메모장 작성 기록";
        return ScheduleResponse.builder()
                .isDeletable(false)
                .start(notepadEntity.getChgDate())
                .title(title)
                .build();
    }
}
