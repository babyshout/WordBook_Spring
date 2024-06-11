package kopo.data.wordbook.app.student.social.naver.rest.client.response;

public record NidMeResponse(
        String resultcode,
        String message,
        NaverNidmeData response
) {
    public record NaverNidmeData(
            String id,
            String name,
            String email
    ) {

    }
}
