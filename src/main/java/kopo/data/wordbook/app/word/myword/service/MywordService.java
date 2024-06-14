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
}
