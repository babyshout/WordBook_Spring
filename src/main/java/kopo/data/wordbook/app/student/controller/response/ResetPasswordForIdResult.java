package kopo.data.wordbook.app.student.controller.response;

import lombok.Builder;

@Builder
public record ResetPasswordForIdResult(
        String message,
        Boolean isSuccess
) {

}