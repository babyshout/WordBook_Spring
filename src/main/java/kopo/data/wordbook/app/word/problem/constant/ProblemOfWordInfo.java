package kopo.data.wordbook.app.word.problem.constant;

import kopo.data.wordbook.app.word.problem.controller.request.RandomWordDocumentToSolveResultRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
@Builder
public record ProblemOfWordInfo(
        String studentId,
        String mywordName,
        String wordName
) {
    public static ProblemOfWordInfo of(RandomWordDocumentToSolveResultRequest body, String studentId) {
        return ProblemOfWordInfo.builder()
                .studentId(studentId)
                .wordName(body.wordName())
                .mywordName(body.mywordName())
                .build();
    }

}
