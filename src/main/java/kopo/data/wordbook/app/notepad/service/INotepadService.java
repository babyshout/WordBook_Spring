package kopo.data.wordbook.app.notepad.service;

import kopo.data.wordbook.app.notepad.controller.reponse.CreateNotepadResponse;
import kopo.data.wordbook.app.notepad.controller.reponse.GetNotepadResponse;

import java.util.List;

public interface INotepadService {

    GetNotepadResponse getNotepad_ByNotepadIdAndStudentId(Long notepadSeq, String studentId);

    List<GetNotepadResponse> getNotepadList_ByNotepadSeqAndStudentId(
            String studentId
    );

    CreateNotepadResponse createNotepad(String content, String studentId);

    CreateNotepadResponse updateNotepad(Long notepadSeq, String content, String studentId);
}
