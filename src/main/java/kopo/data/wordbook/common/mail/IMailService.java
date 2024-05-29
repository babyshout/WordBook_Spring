package kopo.data.wordbook.common.mail;

public interface IMailService {
    public boolean doSendMail(String toMail, String title, String contents);
}
