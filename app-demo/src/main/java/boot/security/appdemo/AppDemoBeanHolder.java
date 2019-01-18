package boot.security.appdemo;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@PropertySource("classpath:db.properties")
public class AppDemoBeanHolder {

  @Autowired private Environment env;

  @Value("classpath:schema.sql")
  private Resource schemaScript;

  @Value("classpath:data.sql")
  private Resource dataScript;

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .driverClassName(env.getProperty(AppDemoConstants.MYSQL_DRIVER_KEY))
        .url(env.getProperty(AppDemoConstants.MYSQL_URL_KEY))
        .username(env.getProperty(AppDemoConstants.MYSQL_USERNAME_KEY))
        .password(env.getProperty(AppDemoConstants.MYSQL_PASSWORD_KEY))
        .build();
  }

  @Bean
  public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
    final DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    initializer.setDatabasePopulator(databasePopulator());
    return initializer;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource());
  }

  private DatabasePopulator databasePopulator() {
    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScripts(schemaScript, dataScript);
    return populator;
  }
}
