package kopo.data.wordbook.app.controller.request;

import lombok.Builder;

@Builder
public record LoginRequestBody(
        String studentId,
        String password
) {
}
