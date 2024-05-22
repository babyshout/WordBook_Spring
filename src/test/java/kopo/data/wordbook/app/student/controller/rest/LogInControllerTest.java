package kopo.data.wordbook.app.student.controller.rest;

import com.google.gson.Gson;
import kopo.data.wordbook.app.student.controller.exception.handler.GlobalExceptionHandler;
import kopo.data.wordbook.app.student.service.IStudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
class LogInControllerTest {

    @InjectMocks
    private LogInController logInController;

    @MockBean
    private IStudentService studentService;

    private MockMvc mockMvc;
    private MockHttpSession session;
    private Gson gson;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(logInController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
//        WebApplicationContext wac;
        this.session = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
        this.gson = new Gson();
    }

    @AfterEach
    void tearDown() {
    }

    private LogInController.LoginSessionInformation getLoginSessionInformation_when_login_is_done() {
        return LogInController.LoginSessionInformation.builder().name("testName")
                .studentId("testId")
                .email("test@email").build();
    }

    @Test
    void getLoginSessionInformation() throws Exception {
        // given
        final String loginSessionUrl =
                LogInController.HandleURL.Paths.getFullLoginSessionInformationPath();
        session.setAttribute(LogInController.LoginSessionInformation.class.getName(),
                getLoginSessionInformation_when_login_is_done()
        );
        System.out.println(loginSessionUrl);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(loginSessionUrl)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(resultActions.andReturn().getResponse().getContentAsString());

        final LogInController.LoginSessionInformation sessionInfoResponse =
                gson.fromJson(
                        resultActions.andReturn().getResponse().getContentAsString(),
                        LogInController.LoginSessionInformation.class
                );

        LogInController.LoginSessionInformation actualSessionInfo =
                getActualSessionInfo();
        Assertions.assertThat(actualSessionInfo).isEqualTo(sessionInfoResponse);
    }

    private LogInController.LoginSessionInformation getActualSessionInfo() {
        return (LogInController.LoginSessionInformation)
                session.getAttribute(LogInController.LoginSessionInformation.class.getName());

    }

    @Test
    void deleteLoginSessionInformation() {
    }
}