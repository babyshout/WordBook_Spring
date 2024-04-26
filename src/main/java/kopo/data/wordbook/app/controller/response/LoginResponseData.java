package kopo.data.wordbook.app.controller.response;

import lombok.Builder;

@Builder
public record LoginResponseData(
        String studentId,
        String name,
        Boolean isLogin
) {
}
