package kopo.data.wordbook.app.calendar.exception;

import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class ScheduleException extends RuntimeException{
    public ScheduleException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public final HttpStatus status;
    public final String message;

}
