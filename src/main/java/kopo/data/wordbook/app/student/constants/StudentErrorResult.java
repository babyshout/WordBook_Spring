package kopo.data.wordbook.app.student.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StudentErrorResult {
    DUPLICATED_MEMBERSHIP_REGISTER(
            HttpStatus.BAD_REQUEST,
            "Duplicated Student Register Requeest"
    ),
    UNKNOWN_EXCEPTION(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Unknown Error"
    ),
    NO_ENTITY(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error"
    ),
    ;


    private final HttpStatus httpStatus;
    private final String message;
    }
