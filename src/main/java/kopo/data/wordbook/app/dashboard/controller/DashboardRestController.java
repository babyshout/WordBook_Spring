package kopo.data.wordbook.app.dashboard.controller;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.dashboard.service.DashboardService;
import kopo.data.wordbook.app.notepad.controller.reponse.GetNotepadResponse;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/dashboard/v1")
public class DashboardRestController {
    private final DashboardService dashboardService;
    public static final class MappedUrl {
        public static final String getRecentlyNotepadList = "/notepad/list";
    }

    @GetMapping(MappedUrl.getRecentlyNotepadList)
    public ResponseEntity<List<GetNotepadResponse>> getRecentlyNotepadList(
            HttpSession session,
            @RequestParam Long amount
    ) {
        log.trace("amount -> {}", amount);
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        List<GetNotepadResponse> responseList =
                dashboardService.getRecentlyNotepadList(sessionInfo.studentId(), amount);

        return ResponseEntity.ok(responseList);
    }
}
