package kopo.data.wordbook.app.controller.request;

import lombok.Builder;

@Builder
public record CreateStudentRequest(
String studentId,
String name,
String email,
String emailVerificationCode,
String password,
String passwordConfirm

) {
}
