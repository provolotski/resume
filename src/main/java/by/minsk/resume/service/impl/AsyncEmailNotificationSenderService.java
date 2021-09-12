package by.minsk.resume.service.impl;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.model.NotificationMessage;
import by.minsk.resume.service.NotificationSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import java.util.concurrent.ExecutorService;

public class AsyncEmailNotificationSenderService implements NotificationSendService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AsyncEmailNotificationSenderService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ExecutorService executorService;

    @Value("${email.fromEmail}")
    private String fromEmail;

    @Value("${email.fromName}")
    private String fromName;

    @Value("${email.sendTryCount}")
    private int tryCount;

    @Override
    public void sendNotification(NotificationMessage message) {
        executorService.submit(new EmailItem(message, tryCount));
    }

    @Override
    public String getDestinationAddress(Profile profile) {
        return profile.getEmail();
    }

    private class EmailItem implements Runnable {
        private final NotificationMessage notificationMessage;
        private int tryCount;

        private EmailItem(NotificationMessage notificationMessage, int tryCount) {
            super();
            this.notificationMessage = notificationMessage;
            this.tryCount = tryCount;
        }

        @Override
        public void run() {
            try {
                LOGGER.debug("Send a new email to {}", notificationMessage.getDestinationAddress());
                MimeMessageHelper message = new MimeMessageHelper(javaMailSender.createMimeMessage(), false);
                message.setSubject(notificationMessage.getSubject());
                message.setTo(new InternetAddress(notificationMessage.getDestinationAddress(), notificationMessage.getDestinationName()));
                message.setFrom(fromEmail, fromName);
                message.setText(notificationMessage.getContent());
                MimeMailMessage msg = new MimeMailMessage(message);
                javaMailSender.send(msg.getMimeMessage());
                LOGGER.debug("Email to {} successful sent", notificationMessage.getDestinationAddress());
            } catch (Exception e) {
                LOGGER.error("Can't send email to " + notificationMessage.getDestinationAddress() + ": " + e.getMessage(), e);
                tryCount--;
                if (tryCount > 0) {
                    LOGGER.debug("Decrement tryCount and try again to send email: tryCount={}, destinationEmail={}", tryCount, notificationMessage.getDestinationAddress());
                    executorService.submit(this);
                } else {
                    LOGGER.error("Email not sent to " + notificationMessage.getDestinationAddress());
                }
            }

        }
    }
}
