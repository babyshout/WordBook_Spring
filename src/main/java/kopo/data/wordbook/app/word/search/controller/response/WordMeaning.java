package kopo.data.wordbook.app.word.search.controller.response;

public record WordMeaning(
        String wordName,
        String definition,
        String type,
        String pos,
        String supNo
) {
}
