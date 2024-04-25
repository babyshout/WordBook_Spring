package kopo.data.wordbook.app.dto;

import kopo.data.wordbook.app.controller.request.CreateStudentRequest;
import lombok.Builder;

import java.time.LocalDate;


@Builder
public record StudentDTO(
        String studentId,
        String password,
        String email,
        String name,
        String regId,
        LocalDate regDate,
        String changerId,
        LocalDate changerDate
) {
    /**
     * Student 생성 요청 왔을때, 해당 body 를 던져줘서 StudentDTO 를 보내줌
     *
     * @param studentRequest 클라이언트 요청 body
     * @return 요청 body 를 DTO 로 바꿔서 반환
     */
    public static StudentDTO of(CreateStudentRequest studentRequest) {

        return builder()
                .studentId(studentRequest.studentId())
                .password(studentRequest.password())
                .email(studentRequest.email())
                .name(studentRequest.name())
                .regId(studentRequest.studentId())
                .regDate(LocalDate.now())
                .changerId(studentRequest.studentId())
                .changerDate(LocalDate.now())

                .build();
    }
}
