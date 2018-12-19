package boot.security.core.validation.code;

public interface SmsCodeSender {

  ValidationCode send(String phone, String code);
}
