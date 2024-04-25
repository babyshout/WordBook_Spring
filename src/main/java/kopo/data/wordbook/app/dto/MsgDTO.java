package kopo.data.wordbook.app.dto;

import lombok.Builder;

@Builder
public record MsgDTO(
        Boolean result,
        String message
) {
}
