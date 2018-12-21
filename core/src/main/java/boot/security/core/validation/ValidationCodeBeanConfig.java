package boot.security.core.validation;

import boot.security.core.properties.SecurityProperties;
import boot.security.core.validation.image.ImageCode;
import boot.security.core.validation.image.ImageValidationCodeGenerator;
import boot.security.core.validation.sms.SmsValidationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationCodeBeanConfig {

  @Autowired private SecurityProperties securityProperties;

  @Bean
  @ConditionalOnMissingBean(name = "imageCodeGenerator")
  public ValidationCodeGenerator<ImageCode> imageCodeGenerator() {
    ValidationCodeGenerator<ImageCode> imageCodeGenerator = new ImageValidationCodeGenerator();
    ((ImageValidationCodeGenerator) imageCodeGenerator).setSecurityProperties(securityProperties);
    return imageCodeGenerator;
  }

  @Bean
  @ConditionalOnMissingBean(SmsValidationCodeGenerator.class)
  public ValidationCodeGenerator<ValidationCode> smsCodeGenerator() {
    ValidationCodeGenerator<ValidationCode> smsCodeGenerator = new SmsValidationCodeGenerator();
    ((SmsValidationCodeGenerator) smsCodeGenerator).setSecurityProperties(securityProperties);
    return smsCodeGenerator;
  }
}
