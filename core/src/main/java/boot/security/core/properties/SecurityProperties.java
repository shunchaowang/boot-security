package boot.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "boot.security")
public class SecurityProperties {

  private BrowserProperties browser = new BrowserProperties();

  private ValidationCodeProperties validation = new ValidationCodeProperties();

  public BrowserProperties getBrowser() {
    return browser;
  }

  public void setBrowser(BrowserProperties browser) {
    this.browser = browser;
  }

  public ValidationCodeProperties getValidation() {
    return validation;
  }

  public void setValidation(ValidationCodeProperties validation) {
    this.validation = validation;
  }
}
