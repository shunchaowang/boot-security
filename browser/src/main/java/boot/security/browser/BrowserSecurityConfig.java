package boot.security.browser;

import boot.security.core.properties.SecurityProperties;
import boot.security.core.validation.ValidationCodeFilter;
import boot.security.core.validation.ValidationCodeProcessor;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private SecurityProperties securityProperties;

  @Autowired private AuthenticationSuccessHandler bootAuthenticationSuccessHandler;

  @Autowired private AuthenticationFailureHandler bootAuthenticationFailureHandler;

  @Autowired private UserDetailsService myUserDetailsService;

  @Autowired private Map<String, ValidationCodeProcessor> validationCodeProcessors;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  //  public PersistentTokenRepository persistentTokenRepository() {
  //    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
  ////    tokenRepository.setCreateTableOnStartup(true);
  //
  //    return tokenRepository;
  //  }
  /**
   * Override this method to configure the {@link HttpSecurity}. Typically subclasses should not
   * invoke this method by calling super as it may override their configuration. The default
   * configuration is:
   *
   * <pre>
   * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
   * </pre>
   *
   * @param http the {@link HttpSecurity} to modify
   * @throws Exception if an error occurs
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    ValidationCodeFilter validationCodeFilter = new ValidationCodeFilter();
    validationCodeFilter.setAuthenticationFailureHandler(bootAuthenticationFailureHandler);
    validationCodeFilter.setSecurityProperties(securityProperties);
    validationCodeFilter.setValidationCodeProcessors(validationCodeProcessors);
    validationCodeFilter.afterPropertiesSet();

    http.addFilterBefore(validationCodeFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin()
        .loginPage("/authentication/require")
        .loginProcessingUrl("/authentication/form")
        .successHandler(bootAuthenticationSuccessHandler)
        .failureHandler(bootAuthenticationFailureHandler)
        .and()
        .rememberMe()
        .userDetailsService(myUserDetailsService)
        //        .tokenRepository(persistentTokenRepository())
        //        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeTokenSeconds())
        .and()
        .authorizeRequests()
        .antMatchers(
            "/authentication/require",
            "/code/*",
            "/favicon.ico",
            securityProperties.getBrowser().getLoginPage())
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable();
    //    http.authorizeRequests().antMatchers("/").permitAll();
  }
}
