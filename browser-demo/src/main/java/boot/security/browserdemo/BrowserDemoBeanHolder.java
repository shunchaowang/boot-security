package boot.security.browserdemo;

import boot.security.core.properties.SecurityConstants;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:db.properties")
public class BrowserDemoBeanHolder {

  @Autowired private Environment env;

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .driverClassName(env.getProperty(SecurityConstants.MYSQL_DRIVER_KEY))
        .url(env.getProperty(SecurityConstants.MYSQL_URL_KEY))
        .username(env.getProperty(SecurityConstants.MYSQL_USERNAME_KEY))
        .password(env.getProperty(SecurityConstants.MYSQL_PASSWORD_KEY))
        .build();
  }
}
