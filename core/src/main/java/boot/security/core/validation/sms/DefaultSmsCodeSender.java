package boot.security.core.validation.sms;

import org.springframework.stereotype.Component;

@Component
public class DefaultSmsCodeSender implements SmsCodeSender {

  @Override
  public void send(String mobile, String code) {
    System.out.println("SMS Code " + code + " sent to " + mobile);
  }
}
