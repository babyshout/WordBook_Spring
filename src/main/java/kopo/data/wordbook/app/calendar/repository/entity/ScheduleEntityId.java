package kopo.data.wordbook.app.calendar.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntityId implements Serializable {
    private Long scheduleSeq;
    private String studentId;
//    private String title;
//    private LocalDate start;
}
