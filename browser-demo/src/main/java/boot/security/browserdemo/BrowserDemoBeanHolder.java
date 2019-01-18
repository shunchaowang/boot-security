package boot.security.browserdemo;

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
        .driverClassName(env.getProperty(BrowserDemoConstants.MYSQL_DRIVER_KEY))
        .url(env.getProperty(BrowserDemoConstants.MYSQL_URL_KEY))
        .username(env.getProperty(BrowserDemoConstants.MYSQL_USERNAME_KEY))
        .password(env.getProperty(BrowserDemoConstants.MYSQL_PASSWORD_KEY))
        .build();
  }
}
