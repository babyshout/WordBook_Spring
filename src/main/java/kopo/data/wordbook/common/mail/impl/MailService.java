package kopo.data.wordbook.common.mail.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kopo.data.wordbook.common.mail.IMailService;
import kopo.data.wordbook.common.mail.MailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public boolean doSendMail(String toMail, String title, String contents) {
        if (!isValidParameter(toMail, title, contents)) {
            log.warn("doSendMail 실패!!!");
//            return false;
            throw new MailException("mail 전송 실패");
        }

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        try {
            messageHelper.setTo(toMail);
            messageHelper.setFrom(fromMail);
            messageHelper.setSubject(title);
            messageHelper.setText(contents);

            mailSender.send(message);
        } catch (MessagingException e) {
            /* MessagingException 을 그대로 던져주고 싶었으나..
            RuntimeException 을 상속하지 않아서 던질수 없음..
             */
            throw new MailException(e);
        }

        return true;
    }

    private boolean isValidParameter(String toMail, String title, String contents) {
        if (toMail.isEmpty()) {
            log.warn("toMail 이 비어있음");
            log.trace("toMail : {}", toMail);
            return false;
        }
        if (title.isEmpty()) {
            log.warn("toMail 이 비어있음");
            log.trace("title : {}", title);
            return false;
        }
        if (contents.isEmpty()) {
            log.warn("toMail 이 비어있음");
            log.trace("contents : {}", contents);
            return false;
        }
        return true;
    }
}
