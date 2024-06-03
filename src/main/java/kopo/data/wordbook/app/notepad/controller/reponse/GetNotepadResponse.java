package kopo.data.wordbook.app.notepad.controller.reponse;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetNotepadResponse(
        Long notepadSeq,
        String content,
        LocalDate regDate,
        LocalDate chgDate
) {
}
