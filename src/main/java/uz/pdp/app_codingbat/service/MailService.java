package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.config.prop.AppProp;
import uz.pdp.app_codingbat.entity.User;
import uz.pdp.app_codingbat.email.MailSenderService;
import uz.pdp.app_codingbat.email.payload.SendEmailDto;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSenderService mailSenderService;
    private final AppProp appProp;

    public void sendActivationEmail(User user) {

        String url = appProp.getMail().getActivateUrl() + user.getEmail();

        SendEmailDto sendEmailDto = SendEmailDto.builder()
                .to(user.getEmail())
                .subject("Activation Email For CodingBat")
                .body("<a href=\"%s\">CLICK_LINK</a>".formatted(url))
                .html(true)
                .build();
        mailSenderService.send(sendEmailDto);
    }

}
