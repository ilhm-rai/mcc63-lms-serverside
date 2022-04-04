package co.id.mii.mcc63lmsserverside.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import co.id.mii.mcc63lmsserverside.util.EmailSender;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

  private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
  private final JavaMailSender mailSender;

  @Override
  @Async
  public void send(String to, String email) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
      helper.setText(email, true);
      helper.setTo(to);
      helper.setSubject("Verify your email");
      helper.setFrom("pandemy@gmail.com");
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      LOGGER.error("Failed to send email", e);
      throw new IllegalStateException("Failed to send email");
    }
  }

}
