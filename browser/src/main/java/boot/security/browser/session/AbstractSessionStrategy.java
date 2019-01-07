package boot.security.browser.session;

import boot.security.browser.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public class AbstractSessionStrategy {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  /** 跳转的url */
  private String destinationUrl;
  /** 重定向策略 */
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  /** 跳转前是否创建新的session */
  private boolean createNewSession = true;

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Must check if the url is valid before redirection.
   *
   * @param invalidSessionUrl
   */
  public AbstractSessionStrategy(String invalidSessionUrl) {

    Assert.isTrue(
        UrlUtils.isValidRedirectUrl(invalidSessionUrl),
        "url must start with '/' or with 'http(s)'");

    this.destinationUrl = invalidSessionUrl;
  }

  /**
   * @see org.springframework.security.web.session.InvalidSessionStrategy#
   *     onInvalidSessionDetected(javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
  protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    if (createNewSession) {
      request.getSession();
    }

    String sourceUrl = request.getRequestURI();
    String targetUrl;

    if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
      targetUrl = destinationUrl + ".html";
      logger.info("Session invalid, redirect to " + targetUrl);
      redirectStrategy.sendRedirect(request, response, targetUrl);
    } else {
      String message = "Invalid session";
      if (isConcurrent()) {
        message = message + ", it might be due to concurrent login.";
      }

      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse<>(message)));
    }
  }

  /**
   * If the invalid session occurs due to concurrent requests.
   *
   * @return
   */
  protected boolean isConcurrent() {
    return false;
  }

  /** @param createNewSession */
  public void setCreateNewSession(boolean createNewSession) {
    this.createNewSession = createNewSession;
  }
}
