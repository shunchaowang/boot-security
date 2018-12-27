package boot.security.browser;

import boot.security.core.properties.SecurityProperties;
import boot.security.core.validation.ValidationCodeFilter;
import boot.security.core.validation.ValidationCodeProcessor;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component("validationCodeSecurityConfig")
public class ValidationCodeSecurityConfig
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired private SecurityProperties securityProperties;

  @Autowired private AuthenticationFailureHandler bootAuthenticationFailureHandler;

  @Autowired private Map<String, ValidationCodeProcessor> validationCodeProcessors;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    ValidationCodeFilter validationCodeFilter = new ValidationCodeFilter();
    validationCodeFilter.setAuthenticationFailureHandler(bootAuthenticationFailureHandler);
    validationCodeFilter.setSecurityProperties(securityProperties);
    validationCodeFilter.setValidationCodeProcessors(validationCodeProcessors);
    validationCodeFilter.afterPropertiesSet();

    http.addFilterBefore(validationCodeFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
