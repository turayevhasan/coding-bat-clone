package uz.pdp.app_codingbat.email;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.config.prop.AppProp;
import uz.pdp.app_codingbat.email.payload.SendEmailDto;
import uz.pdp.app_codingbat.utils.CoreUtils;

@Service
public class MailSenderService {

    private static final Logger log = LoggerFactory.getLogger(MailSenderService.class);
    private final AppProp.Mail mailProp;
    private final JavaMailSender javaMailSender;

    public MailSenderService(AppProp appProp, JavaMailSender javaMailSender) {
        this.mailProp = appProp.getMail();
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void send(SendEmailDto emailDto) {
        try {
            log.info("Sending email to {}", emailDto.getTo());

            emailDto.setFrom(CoreUtils.getIfExists(emailDto.getFrom(), mailProp.getFrom()));

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(emailDto.getFrom());
            mimeMessageHelper.setTo(emailDto.getTo());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody(), emailDto.isHtml());

            javaMailSender.send(mimeMessage);

            log.info("Email sent to {}", emailDto.getTo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}