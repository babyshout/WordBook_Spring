package kopo.data.wordbook.app.dashboard.service;

import kopo.data.wordbook.app.notepad.controller.reponse.GetNotepadResponse;

import java.util.List;

public interface DashboardService {
    /**
     * studentId 의 notepad 를 amount 만큼 최근순으로 가져옴
     *
     * @param studentId 조회시 사용할 id
     * @param amount    가져올 양
     * @return 최근순으로 가져옴..
     */
    List<GetNotepadResponse> getRecentlyNotepadList(String studentId, Long amount);
}
