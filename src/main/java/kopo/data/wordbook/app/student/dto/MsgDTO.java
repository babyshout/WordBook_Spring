package kopo.data.wordbook.app.student.dto;

import lombok.Builder;

@Builder
public record MsgDTO(
        Boolean result,
        String message
) {
}
