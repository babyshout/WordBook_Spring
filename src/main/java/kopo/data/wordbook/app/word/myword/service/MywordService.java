package kopo.data.wordbook.app.word.myword.service;

import kopo.data.wordbook.app.word.myword.controller.response.SimpleMywordResponse;

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
}
