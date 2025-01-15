package org.io.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.io.spring.client.MailSendClient;
import org.io.spring.domain.history.MailSendHistory;
import org.io.spring.domain.history.MailSendHistoryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                .fromEmail(fromEmail)
                .toEmail(toEmail)
                .subject(subject)
                .content(content)
                .build());
            return true;
        }

        return false;
    }

}
