package kopo.data.wordbook.app.word.myword.service;

import kopo.data.wordbook.app.word.myword.controller.request.PostWordNameToMywordRequest;
import kopo.data.wordbook.app.word.myword.controller.response.MywordResponse;
import kopo.data.wordbook.app.word.myword.controller.response.SimpleMywordResponse;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;

import java.util.List;

public interface MywordService {

    /**
     * studentId 로 MywordEntity List 를 조회하고, SimpleMywordResponse 로 바꿔서 리턴함!!
     * @param studentId 조회할 학생 아이디
     * @return List of {@link SimpleMywordResponse}
     */
    List<SimpleMywordResponse> getSimpleMywordList(String studentId);

    /**
     * 제공받은 아이디에 새로운 Myword 를 추가함
     * @param newMywordName 추가할 단어장의 이름
     * @param studentId 추가할 사용자 아이디
     * @return 모든과정이 성공적으로 끝나면, SimpleMywordResponse 를 리턴함..
     */
    List<SimpleMywordResponse> addNewMyword(String newMywordName, String studentId);

    /**
     * body 에서 추가할 단어이름, 단어장이름 추출해서 studentId 에 넣어줌
     * @param body {@link PostWordNameToMywordRequest} 추가할 단어 이름, 단어장 이름이 들어있음
     * @param studentId 추가할 사용자 아이디
     * @return 추가된 결과를 가져온 MywordEntity 를 MywordResponse 로 바꿔서 리턴함
     */
    MywordResponse postWordNameToMyword(PostWordNameToMywordRequest body, String studentId);

    /**
     * wordName 을 studentId 의 mywordName 에 추가함!
     * @param wordName
     * @param mywordName
     * @param studentId
     * @return 추가된 {@link MywordEntity}
     */
    MywordEntity addWordNameToMywordOfStudentId(String wordName, String mywordName, String studentId);
}
