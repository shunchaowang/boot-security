package boot.security.core.properties;

public class BrowserProperties {

  private SessionProperties session = new SessionProperties();

  private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

  private LoginType loginType = LoginType.JSON;

  private int rememberMeTokenSeconds = 3600;

  public int getRememberMeTokenSeconds() {
    return rememberMeTokenSeconds;
  }

  public void setRememberMeTokenSeconds(int rememberMeTokenSeconds) {
    this.rememberMeTokenSeconds = rememberMeTokenSeconds;
  }

  public SessionProperties getSession() {
    return session;
  }

  public void setSession(SessionProperties session) {
    this.session = session;
  }

  public String getLoginPage() {
    return loginPage;
  }

  public void setLoginPage(String loginPage) {
    this.loginPage = loginPage;
  }

  public LoginType getLoginType() {
    return loginType;
  }

  public void setLoginType(LoginType loginType) {
    this.loginType = loginType;
  }
}
