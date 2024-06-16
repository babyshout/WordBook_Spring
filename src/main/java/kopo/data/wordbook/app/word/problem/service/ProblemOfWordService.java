package kopo.data.wordbook.app.word.problem.service;


import jakarta.servlet.http.HttpSession;
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
}
