package boot.security.core.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

@Component("validationCodeSecurityConfig")
public class ValidationCodeSecurityConfig
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired private ValidationCodeFilter validationCodeFilter;

  @Override
  public void configure(HttpSecurity http) throws Exception {

    //    http.addFilterBefore(validationCodeFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(validationCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
  }
}
