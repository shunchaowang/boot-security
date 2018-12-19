package boot.security.core.validation.code;

import boot.security.core.properties.SecurityProperties;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

public class SmsCodeGenerator implements ValidationCodeGenerator<ValidationCode> {

  private SecurityProperties securityProperties;

  @Override
  public ValidationCode generate(HttpServletRequest request) {

    int length = securityProperties.getValidation().getCode().getLength();

    // create a random number
    Random random = new Random();
    StringBuffer code = new StringBuffer();

    // generate random color for different number
    int red = 0, green = 0, blue = 0;
    for (int i = 0; i < length; i++) {
      String s = String.valueOf(random.nextInt(10));
      code.append(s);
    }

    return new ValidationCode(code.toString(), 60);
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
