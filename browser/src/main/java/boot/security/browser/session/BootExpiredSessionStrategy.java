package boot.security.browser.session;

import java.io.IOException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

public class BootExpiredSessionStrategy implements SessionInformationExpiredStrategy {

  @Override
  public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {

    event.getResponse().setContentType("application/json;charset=UTF-8");
    event.getResponse().getWriter().write("Concurrent 登录 Session");
  }
}
