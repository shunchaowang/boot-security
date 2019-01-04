package boot.security.core.validation.sms;

import boot.security.core.properties.SecurityProperties;
import boot.security.core.validation.ValidationCode;
import boot.security.core.validation.ValidationCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidationCodeGenerator")
public class SmsValidationCodeGenerator implements ValidationCodeGenerator<ValidationCode> {

  @Autowired private SecurityProperties securityProperties;

  @Override
  public ValidationCode generate(ServletWebRequest request) {

    int length = securityProperties.getValidation().getSms().getLength();

    String code = RandomStringUtils.randomNumeric(length);

    return new ValidationCode(code, securityProperties.getValidation().getSms().getExpiredIn());
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
