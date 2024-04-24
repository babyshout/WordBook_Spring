package kopo.data.wordbook.app.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Getter
@Setter
public class CommonApiResponse<T> {
    private HttpStatus httpStatus;
    private String message;
    private T data;

    /**
     * 메시지 구조 만들기
     *
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

    /**
     * API 요청에 대한 HTTP 상태 정보 포함하여 응답메시지 생성
     *
     * @param httpStatus
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static <T> @NotNull CommonApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new CommonApiResponse<>(httpStatus, message, data);
    }

    public static ResponseEntity<CommonApiResponse> getError(BindingResult bindingResult) {
        return ResponseEntity.badRequest()
                .body(CommonApiResponse.of(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.series().name(),
                        bindingResult.getAllErrors()));
    }
}
