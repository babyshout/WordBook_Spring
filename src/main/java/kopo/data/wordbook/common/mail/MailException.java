package kopo.data.wordbook.common.mail;

import jakarta.mail.MessagingException;

public class MailException extends RuntimeException{
    private MessagingException e;
    public MailException(MessagingException e) {
        super(e);
        this.e = e;
    }
}
