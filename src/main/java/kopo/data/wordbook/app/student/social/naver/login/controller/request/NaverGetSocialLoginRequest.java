package kopo.data.wordbook.app.student.social.naver.login.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NaverGetSocialLoginRequest(
        @NotNull(message = "code 가 비어있음")
        String code,
        @NotNull(message = "state 가 비어있음")
        String state
) {
}
