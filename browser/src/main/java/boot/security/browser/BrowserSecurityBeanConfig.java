package boot.security.browser;

import boot.security.browser.session.BootExpiredSessionStrategy;
import boot.security.browser.session.BootInvalidSessionStrategy;
import boot.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class BrowserSecurityBeanConfig {

  @Autowired private SecurityProperties securityProperties;

  @Bean
  @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
  public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
    return new BootExpiredSessionStrategy(
        securityProperties.getBrowser().getSession().getSessionInvalidUrl());
  }

  @Bean
  @ConditionalOnMissingBean(InvalidSessionStrategy.class)
  public InvalidSessionStrategy invalidSessionStrategy() {
    return new BootInvalidSessionStrategy(
        securityProperties.getBrowser().getSession().getSessionInvalidUrl());
  }
}
