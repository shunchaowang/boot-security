package boot.security.browser.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.InvalidSessionStrategy;

public class BootInvalidSessionStrategy extends AbstractSessionStrategy
    implements InvalidSessionStrategy {

  public BootInvalidSessionStrategy(String invalidSessionUrl) {
    super(invalidSessionUrl);
  }

  @Override
  public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    onSessionInvalid(request, response);
  }
}
