package kopo.data.wordbook.app.word.comment.controller.request;

import jakarta.validation.constraints.NotNull;

public record CommentPostRequest(
        @NotNull(message = "댓글 내용이 비어있습니다!")
        String content
) {
}
