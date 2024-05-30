package kopo.data.wordbook.app.student.exception.handler;

import kopo.data.wordbook.app.student.constants.StudentErrorResult;
import kopo.data.wordbook.app.student.exception.StudentException;
import kopo.data.wordbook.common.mail.MailException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Customize the handling of {@link MethodArgumentNotValidException}.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception to handle
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} for the response to use, possibly
     * {@code null} when the response is already committed
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        final List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        log.warn("Invalid DTO Parameter errors : {}", errorList);

        return this.makeErrorResponseEntity(errorList.toString());
    }

    /**
     * 바로 위의 handleMethodArgumentNotValid 에서만 사용됨!!
     *
     * @param errorDescription
     * @return
     */
    private ResponseEntity<Object> makeErrorResponseEntity(
            final String errorDescription
    ) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorDescription)
        );
    }

    /**
     * 아래와 같은 패턴으로 예외 처리하기!!
     *
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.warn("Exception occur: ", e);
        return this.makeErrorResponseEntity(
                StudentErrorResult.UNKNOWN_EXCEPTION
        );
    }


    @ExceptionHandler({StudentException.class})
    public ResponseEntity<ErrorResponse> handleStudentException(
            final StudentException e
    ) {
        log.warn("Exception occur: ", e);
        return this.makeErrorResponseEntity(
                StudentErrorResult.DUPLICATED_MEMBERSHIP_REGISTER
        );
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(
            final StudentErrorResult errorResult
    ) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(),
                        errorResult.getMessage()));
    }

    /**
     * mail 보낼때 error 나면.. controller 대신 리턴해주는 메서드
     * @param e
     * @return
     */
    @ExceptionHandler({MailException.class})
    private ResponseEntity<ErrorResponse> handleMailException(
            final MailException e) {
        log.warn("MailException occur: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "MAIL_SEND_FAILED",
                        e.getMessage()
                ));
    }


    @Getter
    @RequiredArgsConstructor
    static public class ErrorResponse {
        private final String code;
        private final String message;
    }
}
