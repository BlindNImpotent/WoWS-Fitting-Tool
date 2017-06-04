package WoWSSSC.service;

import WoWSSSC.model.email.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Aesis on 2017-06-04.
 */
@Service
public class MailService
{
    @Autowired
    private JavaMailSender sender;

    public void postEmail(EmailModel email) throws MessagingException
    {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, false);

        helper.setFrom(email.getEmail());
        helper.setTo("admin@wowsft.com");
        helper.setSubject(email.getSubject());
        helper.setText(email.getDescription());

        sender.send(message);
    }
}
