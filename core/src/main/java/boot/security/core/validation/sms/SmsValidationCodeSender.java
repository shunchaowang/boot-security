package boot.security.core.validation.sms;

public interface SmsValidationCodeSender {

  void send(String mobile, String code);
}
