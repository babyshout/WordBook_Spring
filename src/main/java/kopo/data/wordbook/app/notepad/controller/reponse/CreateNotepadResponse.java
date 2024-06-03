package kopo.data.wordbook.app.notepad.controller.reponse;

import lombok.Builder;

@Builder
public record CreateNotepadResponse(
        Long notepadSeq,
        String content
) {
}
