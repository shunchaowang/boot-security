package boot.security.browser;

import boot.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AbstractBrowserSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired protected AuthenticationSuccessHandler bootAuthenticationSuccessHandler;

  @Autowired protected AuthenticationFailureHandler bootAuthenticationFailureHandler;

  protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
    http.formLogin()
        .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
        .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
        .successHandler(bootAuthenticationSuccessHandler)
        .failureHandler(bootAuthenticationFailureHandler);
  }
}
