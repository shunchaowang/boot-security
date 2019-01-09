package boot.security.browser;

import boot.security.core.properties.SecurityConstants;
import boot.security.core.properties.SecurityProperties;
import boot.security.core.validation.ValidationCodeSecurityConfig;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class BrowserSecurityConfig extends AbstractBrowserSecurityConfig {

  @Autowired private SecurityProperties securityProperties;

  @Autowired private UserDetailsService myUserDetailsService;

  @Autowired private ValidationCodeSecurityConfig validationCodeSecurityConfig;

  @Autowired private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

  @Autowired private InvalidSessionStrategy invalidSessionStrategy;

  @Autowired private LogoutSuccessHandler logoutSuccessHandler;

  //  public PersistentTokenRepository persistentTokenRepository() {
  //    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
  //    tokenRepository.setCreateTableOnStartup(true);
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

    // create a List to store all antMatchers, skip empty or blank string
    List<String> permitList = new ArrayList<>();
    permitList.add(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL);
    permitList.add(SecurityConstants.DEFAULT_VALIDATION_CODE_URL_PREFIX + "/*");
    permitList.add(securityProperties.getBrowser().getLoginPage());
    permitList.add(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    if (StringUtils.isNotBlank(securityProperties.getBrowser().getLogoutPageUrl())) {
      permitList.add(securityProperties.getBrowser().getLogoutPageUrl());
    }

    http.apply(validationCodeSecurityConfig)
        .and()
        .rememberMe()
        .userDetailsService(myUserDetailsService)
        //        .tokenRepository(persistentTokenRepository())
        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeTokenSeconds())
        .and()
        .sessionManagement()
        .invalidSessionStrategy(invalidSessionStrategy)
        .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
        .maxSessionsPreventsLogin(
            securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
        .expiredSessionStrategy(sessionInformationExpiredStrategy)
        .and()
        .and()
        .logout()
        .logoutUrl("/signout") // Spring Security default logoutUrl is logout
        // .logoutSuccessUrl("/boot-logout.html") // cannot have both logoutSuccessHandler and
        // logoutSuccessUrl
        .logoutSuccessHandler(logoutSuccessHandler)
        .deleteCookies("JSESSIONID")
        .and()
        .authorizeRequests()
        .antMatchers(permitList.toArray(new String[permitList.size()]))
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable();
  }
}
