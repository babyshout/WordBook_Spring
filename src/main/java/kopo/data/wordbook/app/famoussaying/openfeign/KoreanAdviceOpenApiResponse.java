package kopo.data.wordbook.app.famoussaying.openfeign;

/**
 * 한국어 명언 Open Api 응답형태
 * <pre>
 * JSON 예시
 * {
 *   "author": "에이브러햄 링컨",
 *   "authorProfile": "미국 16대 대통령",
 *   "message": "반드시 이겨야 하는 건 아니지만 진실할 필요는 있다. 반드시 성공해야 하는 건 아니지만, 소신을 가지고 살아야 할 필요는 있다."
 * }
 * </pre>
 */

public record KoreanAdviceOpenApiResponse(
        String author,
        String authorProfile,
        String message
) {
}
