package kopo.data.wordbook.app.student.controller.rest;

import com.google.gson.Gson;
import kopo.data.wordbook.app.student.exception.handler.GlobalExceptionHandler;
import kopo.data.wordbook.app.student.service.IStudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static kopo.data.wordbook.app.student.controller.rest.LogInController.*;

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

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogInControllerTest.class);

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(logInController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
//                ;
//        WebApplicationContext wac = WebApplication
//        WebApplicationContext wac;
        log.error(wac.toString());
        this.session = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
        this.gson = new Gson();

        Assertions.assertThat(this.session.isNew()).isTrue();
    }

    @AfterEach
    void tearDown() {
        // 필요없는 구문이나.. 연습상 집어넣음
        if (!this.session.isInvalid()) {
            session.invalidate();
        }
    }

    private LogInController.LoginSessionInformation getMockLoginSessionInformation() {
        return LogInController.LoginSessionInformation.builder().name("testName_이름")
                .studentId("testId")
                .email("test@email").build();
    }

    @Test
    void getLoginSessionInformation() throws Exception {
        // given
        final String loginSessionUrl =
                LogInController.HandleURL.Paths.getFullLoginSessionInformationPath();
        this.set_MockLoginInformation_atSession(getMockLoginSessionInformation());
        log.trace("loginSessionUrl : " + loginSessionUrl);

        // whenR
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(loginSessionUrl)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        // 해당 메서드가 어떻게 동작하는지 보고싶어서 찍은 로그임
        log.trace("resultActions.andReturn().getResponse().getContentAsString() : "
                + resultActions.andReturn().getResponse().getContentAsString());

        final LogInController.LoginSessionInformation sessionInfoResponse =
                gson.fromJson(
                        resultActions.andReturn().getResponse().getContentAsString(),
                        LogInController.LoginSessionInformation.class
                );

        LogInController.LoginSessionInformation actualSessionInfo =
                getActualSessionInfo();
        Assertions.assertThat(actualSessionInfo).isEqualTo(sessionInfoResponse);
    }

    @Test
    void deleteLoginSessionInformation() throws Exception {
        // given
        final String loginSessionUrl =
                HandleURL.Paths.getFullLoginSessionInformationPath();
        this.set_MockLoginInformation_atSession(getMockLoginSessionInformation());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(loginSessionUrl)
                        .session(this.session)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        final String resultString =
                resultActions.andReturn().getResponse().getContentAsString();
        log.trace("resultString : " + resultString);
        resultActions.andExpect(MockMvcResultMatchers.status().is(200));


        Assertions.assertThat(this.session.isInvalid()).isTrue();
        // 이미 세션이 invalidated 돼서... new 가 아님!!
        // 이런것도 있구나~~
//        this.session.setNew(true);
//        Assertions.assertThat(this.session.isNew()).isTrue();
    }

    private void set_MockLoginInformation_atSession(LoginSessionInformation info) {
        session.setAttribute(LogInController.LoginSessionInformation.class.getName(),
                info
        );
    }

    private LogInController.LoginSessionInformation getActualSessionInfo() {
        return (LogInController.LoginSessionInformation)
                session.getAttribute(LogInController.LoginSessionInformation.class.getName());

    }

}