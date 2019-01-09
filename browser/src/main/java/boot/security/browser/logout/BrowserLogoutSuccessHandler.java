package boot.security.browser.logout;

import boot.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private String logoutUrl;

  private ObjectMapper objectMapper = new ObjectMapper();

  public BrowserLogoutSuccessHandler(String logoutUrl) {
    this.logoutUrl = logoutUrl;
  }

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    logger.info("Logout Successfully");

    if (StringUtils.isBlank(logoutUrl)) {
      response.setContentType("application/json;charset=UTF-8");
      response
          .getWriter()
          .write(objectMapper.writeValueAsString(new SimpleResponse<>("Log Out Successfully")));
    } else {
      response.sendRedirect(logoutUrl);
    }
  }
}
