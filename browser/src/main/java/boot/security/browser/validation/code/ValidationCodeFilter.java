package boot.security.browser.validation.code;

import boot.security.browser.authentication.BootAuthenticationFailureHandler;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ValidationCodeFilter extends OncePerRequestFilter {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private BootAuthenticationFailureHandler bootAuthenticationFailureHandler;

  /**
   * Same contract as for {@code doFilter}, but guaranteed to be just invoked once per request
   * within a single request thread. See {@link #shouldNotFilterAsyncDispatch()} for details.
   *
   * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the default
   * ServletRequest and ServletResponse ones.
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (StringUtils.equals(request.getRequestURI(), "/authentication/form")
        && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
      try {
        validate(request);
      } catch (ValidationCodeException exception) {
        bootAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private void validate(HttpServletRequest request) throws ValidationCodeException {

    HttpSession session = request.getSession();
    ImageCode validationInSession =
        (ImageCode) session.getAttribute(ValidationCodeController.SESSION_KEY);
    logger.info(validationInSession.getCode());
    ServletWebRequest webRequest = new ServletWebRequest(request);
    String codeInRequest = webRequest.getParameter("imageCode");

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidationCodeException("Code Not Exist in Request.");
    }

    if (validationInSession == null) {
      throw new ValidationCodeException("Code Not Exist in Session.");
    }

    if (validationInSession.isExpired()) {
      throw new ValidationCodeException("Code Expired in Session.");
    }

    if (!StringUtils.equals(validationInSession.getCode(), codeInRequest)) {
      throw new ValidationCodeException("Code Not Matched.");
    }

    session.removeAttribute(ValidationCodeController.SESSION_KEY);
  }
}
