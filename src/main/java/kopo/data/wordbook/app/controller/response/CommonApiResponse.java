package kopo.data.wordbook.app.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonApiResponse <T>{
    private HttpStatus httpStatus;
    private String message;
    private T data;

    /**
     * 메시지 구조 만들기
     * @param httpStatus
     * @param message
     * @param data
     */
    @Builder
    public CommonApiResponse(HttpStatus httpStatus, String message, T data) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static <T> @NotNull CommonApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new CommonApiResponse<>(httpStatus, message, data);
    }
}
