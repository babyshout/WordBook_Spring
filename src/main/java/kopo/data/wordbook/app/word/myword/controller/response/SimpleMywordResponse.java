package kopo.data.wordbook.app.word.myword.controller.response;

import lombok.Builder;

/**
 * 단순히 MywordName 만 있음!!!
 * @param mywordName mywordName
 */
@Builder
public record SimpleMywordResponse(
        String mywordName
) {
}
