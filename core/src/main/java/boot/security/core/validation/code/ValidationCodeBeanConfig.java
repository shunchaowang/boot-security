package boot.security.core.validation.code;

import boot.security.core.properties.SecurityProperties;
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
    ValidationCodeGenerator<ImageCode> imageCodeGenerator = new ImageCodeGenerator();
    ((ImageCodeGenerator) imageCodeGenerator).setSecurityProperties(securityProperties);
    return imageCodeGenerator;
  }

  @Bean
  @ConditionalOnMissingBean(SmsCodeGenerator.class)
  public ValidationCodeGenerator<ValidationCode> smsCodeGenerator() {
    ValidationCodeGenerator<ValidationCode> smsCodeGenerator = new SmsCodeGenerator();
    ((SmsCodeGenerator) smsCodeGenerator).setSecurityProperties(securityProperties);
    return smsCodeGenerator;
  }
}
