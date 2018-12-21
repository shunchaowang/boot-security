package boot.security.core.validation.sms;

import boot.security.core.validation.AbstractValidationCodeProcessor;
import boot.security.core.validation.ValidationCode;
import boot.security.core.validation.ValidationCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidationCodeProcessor")
public class SmsValidationCodeProcessor extends AbstractValidationCodeProcessor<ValidationCode> {

  @Autowired private SmsValidationCodeSender smsCodeSender;

  @Override
  protected void send(ServletWebRequest request, ValidationCode validationCode) {

    String mobile;
    try {
      mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
    } catch (ServletRequestBindingException e) {
      throw new ValidationCodeException("Error retrieving param from request.");
    }

    smsCodeSender.send(mobile, validationCode.getCode());
  }
}
