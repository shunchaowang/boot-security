package boot.security.core.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class AuthenticationBeanConfig {

  @Bean
  @ConditionalOnMissingBean(name = "authenticationSuccessHandler")
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new CoreAuthenticationSuccessHandler();
  }

  @Bean
  @ConditionalOnMissingBean(name = "authenticationFailureHandler")
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new CoreAuthenticationFailureHandler();
  }
}
