package to.projekt.to2021projekt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import to.projekt.to2021projekt.hibernate.SessionProvider;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Controller
public class MailController extends Thread {
    private JavaMailSender javaMailSender;
    private String text;
    private String receiverEmail;
    private void sendMail(String to,
                          String subject,
                          String text,
                          boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
        if(SessionProvider.getLoggedUser() != null)
            receiverEmail = SessionProvider.getLoggedUser().getEmail();
    }
    @Autowired
    public MailController() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("masterMindSF1115@gmail.com");
        mailSender.setPassword("mastermind1115");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        javaMailSender = mailSender;
    }

    @Override
    public void run() {
        try {
            this.sendMail(receiverEmail, "Powiadomienie MasterMind",
                    text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMail(MailType mailType) throws MessagingException {
        text = mailType.toString();
        this.start();
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
