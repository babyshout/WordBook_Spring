package kopo.data.wordbook.app.notepad.controller.reponse;

import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetNotepadResponse(
        Long notepadSeq,
        String content,
        LocalDate regDate,
        LocalDate chgDate
) {
    public static GetNotepadResponse of(NotepadEntity notepadEntity) {
        return GetNotepadResponse.builder()
                .notepadSeq(notepadEntity.getNotepadSeq())
                .content(notepadEntity.getContent())
                .chgDate(notepadEntity.getChgDate())
                .regDate(notepadEntity.getRegDate())
                .build();
    }
}
