package boot.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

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
    http.formLogin()
        .loginPage("/boot-login.html")
        .loginProcessingUrl("/authentication/form")
        .and()
        .authorizeRequests()
        .antMatchers("/boot-login.html").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf().disable();
    //    http.authorizeRequests().antMatchers("/").permitAll();
  }
}
