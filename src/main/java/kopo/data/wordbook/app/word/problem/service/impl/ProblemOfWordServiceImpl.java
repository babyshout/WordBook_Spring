package kopo.data.wordbook.app.word.problem.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.repository.MywordRepository;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.myword.service.MywordService;
import kopo.data.wordbook.app.word.problem.constant.ProblemOfWordStatus;
import kopo.data.wordbook.app.word.problem.service.ProblemOfWordService;
import kopo.data.wordbook.app.word.repository.WordRepository;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProblemOfWordServiceImpl implements ProblemOfWordService {
    private final WordRepository wordRepository;
    private final StudentRepository studentRepository;
    private final MywordRepository mywordRepository;

    /**
     * 문제를 풀 {@link WordDocument} 를 studentId 의 mywordName 의 단어장에서 랜덤으로 가져옴
     *
     * @param mywordName 검색할 단어장 이름
     * @param studentId  단어장 소유주 아이디
     * @param session    문제풀이중인지 확인하기 위해 세션을 사용할수도 있음..
     * @return 랜덤으로 꺼내온 {@link WordDocument}
     */
    @Transactional
    @Override
    public WordDocument getRandomWordDocumentToSolve(String mywordName, String studentId, HttpSession session) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("단어문제 생성중 동일한 student 가 없음!!"));

        MywordEntity myword = mywordRepository.findByStudentAndMywordName(student, mywordName)
                .orElseThrow(() -> new RuntimeException("단어문제 생성중 " +
                        student.getStudentId() + "의 " +
                        mywordName + " 단어장이 없음!!"));

        List<String> wordNameList = myword.getWordNameList();

        int size = wordNameList.size();

        log.trace("wordNameList size -> {}", size);
        if (size < 1) {
            throw new RuntimeException(mywordName + "의 wordNameList 사이즈가 [" + size + "] 임!!");
        }
        int randomIndex = new SecureRandom().nextInt(size);
        log.trace("단어를 가져올 randomIndex -> {}", randomIndex);

        String wordName = wordNameList.get(randomIndex);
        log.trace("가져올 단어의 wordName -> {}", wordName);

        WordDocument wordDocument = wordRepository.findByWordName(wordName)
                .orElseThrow(() -> new RuntimeException(wordName + "이란 단어가 mongoDB 에 없음!!"));
        log.trace("가져온 wordDocument -> {}", wordDocument);


        // FIXME 메서드 추출할것!!
        session.setAttribute(ProblemOfWordStatus.class.getName(), ProblemOfWordStatus.PROGRESS);

        ProblemOfWordStatus status =
                (ProblemOfWordStatus) session.getAttribute(ProblemOfWordStatus.class.getName());
        log.trace("status of ProblemOfWordStatus -> {}", status);
        // FIXME 여기까지!!

        return wordDocument;
    }
}
