package kopo.data.wordbook.app.student.mypage.response;

import lombok.Builder;

@Builder
public record EmailAuthCodeResponse(
        String authCode
) {
}
