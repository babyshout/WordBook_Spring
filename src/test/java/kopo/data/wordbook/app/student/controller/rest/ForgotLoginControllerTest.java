package kopo.data.wordbook.app.student.controller.rest;

import com.google.gson.Gson;
import kopo.data.wordbook.app.student.controller.exception.handler.GlobalExceptionHandler;
import kopo.data.wordbook.app.student.service.IStudentService;
import kopo.data.wordbook.app.student.service.implement.StudentService;
import kopo.data.wordbook.common.util.EncryptUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static kopo.data.wordbook.app.student.controller.rest.ForgotLoginController.*;

@ExtendWith(MockitoExtension.class)
class ForgotLoginControllerTest {
    @InjectMocks
    private ForgotLoginController targetController;

    private MockMvc mockMvc;
    @Mock
    private IStudentService studentService;

    private Gson gson;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogInControllerTest.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(targetController)
                .setControllerAdvice(new GlobalExceptionHandler())
                // mockHttpServletResponse 가 관리해서 filter 를 추가해서 처리해 줘야함
                // @WebMvcTest 사용시 없어도 됨..!
//                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .addFilter(((servletRequest, servletResponse, filterChain) -> {
                    servletResponse.setCharacterEncoding("UTF-8");
                    filterChain.doFilter(servletRequest, servletResponse);
                }))
                .build();
        gson = new Gson();
    }

    @Test
    void resetPasswordForId() throws Exception {
        // given
        final String url = "/api/student/v1/forgot/reset-password-for-id";
        ResetPasswordForIdRequestBody requestBody =
                ResetPasswordForIdRequestBody.builder()
                        .studentId("testId")
                        .name("testName")
                        .email("test@email.com").build();
        final String expectMessage =
                StudentService.ResultMessage.SUCCESS_RESET_PASSWORD_FOR_ID.resultMessage;

        Mockito.doReturn(expectMessage)
                .when(studentService)
                .resetPasswordForId(
                        requestBody.studentId(),
                        requestBody.name(),
                        EncryptUtil.encAES128CBC(requestBody.email())
                );

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(requestBody))
//                        .characterEncoding("UTF-8")
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());


        log.error(resultActions.andReturn().getResponse().getCharacterEncoding());
        String resultJsonString = resultActions.andReturn().getResponse().getContentAsString(
                StandardCharsets.UTF_8
        );
        log.error(resultJsonString);
//        log.error(new String());
//        String.format()
//        log.error(gson.fromJson(resultActions.andReturn().getResponse().getContentAsString(), String.class));
        Assertions.assertThat(resultJsonString).isEqualTo(expectMessage);

    }

}