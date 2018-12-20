package boot.security.core.validation.sms;

import boot.security.core.properties.SecurityProperties;
import boot.security.core.validation.ValidationCode;
import boot.security.core.validation.ValidationCodeGenerator;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;

public class SmsCodeGenerator implements ValidationCodeGenerator<ValidationCode> {

  private SecurityProperties securityProperties;

  @Override
  public ValidationCode generate(HttpServletRequest request) {

    int length = securityProperties.getValidation().getSms().getLength();

    String code = RandomStringUtils.randomNumeric(length);

    return new ValidationCode(code, securityProperties.getValidation().getSms().getExpiredIn());
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
