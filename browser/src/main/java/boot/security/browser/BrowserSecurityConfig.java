package boot.security.browser;

import boot.security.core.properties.SecurityConstants;
import boot.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class BrowserSecurityConfig extends AbstractBrowserSecurityConfig {

  @Autowired private SecurityProperties securityProperties;

  @Autowired private UserDetailsService myUserDetailsService;

  @Autowired private ValidationCodeSecurityConfig validationCodeSecurityConfig;

  @Autowired private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

  @Autowired private InvalidSessionStrategy invalidSessionStrategy;

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

    applyPasswordAuthenticationConfig(http);

    http.apply(validationCodeSecurityConfig)
        .and()
        .rememberMe()
        .userDetailsService(myUserDetailsService)
        //        .tokenRepository(persistentTokenRepository())
        //        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeTokenSeconds())
        .and()
        .sessionManagement()
        .invalidSessionStrategy(invalidSessionStrategy)
        .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
        .maxSessionsPreventsLogin(
            securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
        .expiredSessionStrategy(sessionInformationExpiredStrategy)
        .and()
        .and()
        .authorizeRequests()
        .antMatchers(
            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
            SecurityConstants.DEFAULT_VALIDATION_CODE_URL_PREFIX + "/*",
            securityProperties.getBrowser().getLoginPage(),
            securityProperties.getBrowser().getSession().getSessionInvalidUrl())
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable();
    //    http.authorizeRequests().antMatchers("/").permitAll();
  }
}
