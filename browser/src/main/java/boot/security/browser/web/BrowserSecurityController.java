package boot.security.browser.web;

import boot.security.browser.support.SimpleResponse;
import boot.security.core.properties.SecurityProperties;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowserSecurityController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private RequestCache requestCache = new HttpSessionRequestCache();
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired private SecurityProperties securityProperties;

  /**
   * Redirect here when authentication is required.
   *
   * @param request
   * @param response
   * @return
   */
  @GetMapping("/authentication/require")
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public SimpleResponse<String> requireAuthentication(
      HttpServletRequest request, HttpServletResponse response) {

    SavedRequest savedRequest = requestCache.getRequest(request, response);
    if (savedRequest != null) {
      String targetUrl = savedRequest.getRedirectUrl();
      logger.info("Redirected from " + targetUrl);

      if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
        try {
          logger.info("Redirect to login page " + securityProperties.getBrowser().getLoginPage());
          redirectStrategy.sendRedirect(
              request, response, securityProperties.getBrowser().getLoginPage());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return new SimpleResponse<>("Authentication required, please redirect to login page.");
  }

  @GetMapping("/session/invalid")
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public SimpleResponse<String> sessionInvalid() {
    return new SimpleResponse<>("Session Invalid");
  }
}
