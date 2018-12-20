package boot.security.core.validation.sms;

public interface SmsCodeSender {

  void send(String mobile, String code);
}
