package kopo.data.wordbook.app.student.exception;

import kopo.data.wordbook.app.student.constants.StudentErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudentException extends RuntimeException {
    private final StudentErrorResult errorResult;
}
