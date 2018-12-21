package boot.security.core.validation.sms;

import org.springframework.stereotype.Component;

@Component
public class DefaultSmsValidationCodeSender implements SmsValidationCodeSender {

  @Override
  public void send(String mobile, String code) {
    System.out.println("SMS Code " + code + " sent to " + mobile);
  }
}
