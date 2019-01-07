package boot.security.browser.session;

import java.io.IOException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

public class BootExpiredSessionStrategy extends AbstractSessionStrategy
    implements SessionInformationExpiredStrategy {

  /** Must check if the url is valid before redirection. */
  public BootExpiredSessionStrategy(String invalidSessionUrl) {
    super(invalidSessionUrl);
  }

  @Override
  public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {

    //    event.getResponse().setContentType("application/json;charset=UTF-8");
    //    event.getResponse().getWriter().write("Concurrent 登录 Session");

    onSessionInvalid(event.getRequest(), event.getResponse());
  }

  /** If the invalid session occurs due to concurrent requests. */
  @Override
  protected boolean isConcurrent() {
    return true;
  }
}
