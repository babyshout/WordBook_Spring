package kopo.data.wordbook.app.word.problem.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.repository.MywordRepository;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.problem.constant.ProblemOfWordInfo;
import kopo.data.wordbook.app.word.problem.constant.ProblemOfWordStatus;
import kopo.data.wordbook.app.word.problem.controller.request.RandomWordDocumentToSolveResultRequest;
import kopo.data.wordbook.app.word.problem.reopsitory.ProblemOfWordRepository;
import kopo.data.wordbook.app.word.problem.reopsitory.entity.ProblemOfWordEntity;
import kopo.data.wordbook.app.word.problem.service.ProblemOfWordService;
import kopo.data.wordbook.app.word.repository.WordRepository;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProblemOfWordServiceImpl implements ProblemOfWordService {
    private final WordRepository wordRepository;
    private final StudentRepository studentRepository;
    private final MywordRepository mywordRepository;
    private final ProblemOfWordRepository problemOfWordRepository;

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

        ProblemOfWordInfo info = ProblemOfWordInfo.builder()
                .wordName(wordName)
                .mywordName(mywordName)
                .studentId(studentId)
                .build();

        setStatusAsProgress(session, info);


        return wordDocument;
    }


    /**
     * 단어풀이결과 받아서 {@link ProblemOfWordEntity} 에 넣어줌!
     *
     * @param body      풀이결과가 들어있는 {@link RequestBody}
     * @param studentId 문제를 푼 사용자 아이디
     * @param session   유효한지 확인할때 사용할 session
     */
    @Override
    public void postRandomWordDocumentToSolveResult(RandomWordDocumentToSolveResultRequest body, String studentId, HttpSession session) {
        ProblemOfWordInfo info = ProblemOfWordInfo.of(body, studentId);

        validateRequest(info, session);

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("session 의 studnetId 로 studentEntity 찾기 실패!!"));
        log.trace("student -> {}", student);

        MywordEntity myword = mywordRepository.findByStudentAndMywordName(student, body.mywordName())
                .orElseThrow(() -> new RuntimeException("student 와 body.mywordName() 으로 MywordEntity 찾기 실패!!"));
        log.trace("myword -> {}", myword);

        ProblemOfWordEntity saving = ProblemOfWordEntity.builder()
                .wordName(body.wordName())
                .wordNameAnswer(body.wordNameAnswer())
                .mywordEntity(myword)
                .build();
        log.trace("saving -> {}", saving);

        ProblemOfWordEntity saved = problemOfWordRepository.save(saving);
        log.trace("saved -> {}", saved);

        log.info("정상적으로 완료!!");
    }

    private void validateRequest(ProblemOfWordInfo info, HttpSession session) {
        ProblemOfWordStatus status = getStatusOfProblemOfWord(session);
        if (status != ProblemOfWordStatus.PROGRESS) {
            session.invalidate();
            throw new RuntimeException("ProblemOfWordStatus 가 PROGRESS 가 아님!!");
        }

        ProblemOfWordInfo infoOfSession =
                (ProblemOfWordInfo) session.getAttribute(ProblemOfWordInfo.class.getName());
        log.trace("infoOfSession -> {}", infoOfSession);
        if (!infoOfSession.equals(info)) {
            session.invalidate();
            throw new RuntimeException("요청과, session 에 들어있는 내용이 동일하지 않음..!");
        }
    }

    private static void setStatusAsProgress(HttpSession session, ProblemOfWordInfo info) {
        session.setAttribute(ProblemOfWordStatus.class.getName(), ProblemOfWordStatus.PROGRESS);
        session.setAttribute(ProblemOfWordInfo.class.getName(), info);

        ProblemOfWordStatus status =
                (ProblemOfWordStatus) session.getAttribute(ProblemOfWordStatus.class.getName());
        log.trace("status of ProblemOfWordStatus -> {}", status);
        ProblemOfWordInfo infoOfSession =
                (ProblemOfWordInfo) session.getAttribute(ProblemOfWordInfo.class.getName());
        log.trace("infoOfSession -> {}", infoOfSession);
    }

    private static ProblemOfWordStatus getStatusOfProblemOfWord(HttpSession session) {
        ProblemOfWordStatus status =
                (ProblemOfWordStatus) session.getAttribute(ProblemOfWordStatus.class.getName());
        log.trace("status of ProblemOfWordStatus -> {}", status);
        return status;
    }
}
