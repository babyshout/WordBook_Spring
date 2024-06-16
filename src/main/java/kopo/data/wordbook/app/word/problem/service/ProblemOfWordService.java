package kopo.data.wordbook.app.word.problem.service;


import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.word.problem.controller.request.RandomWordDocumentToSolveResultRequest;
import kopo.data.wordbook.app.word.repository.document.WordDocument;

public interface ProblemOfWordService {
    /**
     * 문제를 풀 {@link WordDocument} 를 studentId 의 mywordName 의 단어장에서 랜덤으로 가져옴
     * @param mywordName 검색할 단어장 이름
     * @param studentId 단어장 소유주 아이디
     * @param session 문제풀이중인지 확인하기 위해 세션을 사용할수도 있음..
     * @return 랜덤으로 꺼내온 {@link WordDocument}
     */
    WordDocument getRandomWordDocumentToSolve(String mywordName, String studentId, HttpSession session);

    /**
     * 단어풀이결과 받아서 {@link kopo.data.wordbook.app.word.problem.reopsitory.entity.ProblemOfWordEntity} 에 넣어줌!
     *
     * @param body      풀이결과가 들어있는 {@link org.springframework.web.bind.annotation.RequestBody}
     * @param studentId 문제를 푼 사용자 아이디
     * @param session
     */
    void postRandomWordDocumentToSolveResult(RandomWordDocumentToSolveResultRequest body, String studentId, HttpSession session);
}
