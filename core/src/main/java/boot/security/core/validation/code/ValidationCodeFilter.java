package boot.security.core.validation.code;

import boot.security.core.properties.SecurityProperties;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

public class ValidationCodeFilter extends OncePerRequestFilter implements InitializingBean {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private AuthenticationFailureHandler authenticationFailureHandler;

  private SecurityProperties securityProperties;

  private AntPathMatcher pathMatcher = new AntPathMatcher();

  private Set<String> urls = new HashSet<>();

  /**
   * Calls the {@code initFilterBean()} method that might contain custom initialization of a
   * subclass.
   *
   * <p>Only relevant in case of initialization as bean, where the standard {@code
   * init(FilterConfig)} method won't be called.
   *
   * @see #initFilterBean()
   * @see #init(FilterConfig)
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    String[] configUrls =
        StringUtils.splitByWholeSeparatorPreserveAllTokens(
            securityProperties.getValidation().getCode().getUrl(), ",");
    for (String configUrl : configUrls) {
      urls.add(configUrl);
    }
    urls.add("/authentication/form"); // code validation is required on login
  }

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

    boolean required = false;

    for (String url : urls) {
      if (pathMatcher.match(url, request.getRequestURI())) {
        required = true;
      }
    }

    if (required) {
      try {
        validate(request);
      } catch (ValidationCodeException exception) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  public void setAuthenticationFailureHandler(
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  private void validate(HttpServletRequest request) throws ValidationCodeException {

    HttpSession session = request.getSession();
    ImageCode validationInSession =
        (ImageCode) session.getAttribute(ValidationCodeController.SESSION_KEY);
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

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
