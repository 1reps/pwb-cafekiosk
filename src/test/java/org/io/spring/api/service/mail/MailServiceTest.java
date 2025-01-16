package org.io.spring.api.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.io.spring.client.MailSendClient;
import org.io.spring.domain.history.MailSendHistory;
import org.io.spring.domain.history.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // @Mock 사용 시 추가
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // MailSendClient mailSendClient = mock(MailSendClient.class);
        // MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);
        // MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        // when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
        //     .thenReturn(true);

        // given
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
            .willReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        // 실행 후 검증 수행
        verify(mailSendHistoryRepository, times(1))
            .save(any(MailSendHistory.class));

    }

}