package kopo.data.wordbook.app.student.social.naver.rest.client.response;

import lombok.Builder;

@Builder
public record AuthorizationCodeResponse(
        String access_token,
        String refresh_token,
        String token_type,
        Integer expires_in,
        String error,
        String error_description
) {
}
