package boot.security.core.validation;

import boot.security.core.properties.SecurityConstants;
import boot.security.core.properties.SecurityProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

@Component("validationCodeFilter")
public class ValidationCodeFilter extends OncePerRequestFilter implements InitializingBean {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private AuthenticationFailureHandler authenticationFailureHandler;

  @Autowired private SecurityProperties securityProperties;

  private AntPathMatcher pathMatcher = new AntPathMatcher();

  @Autowired private Map<String, ValidationCodeProcessor> validationCodeProcessors;

  /**
   * we will store all urls into the map, used to check all post url to match.
   * urlMaps["/authentication/form"] = IMAGE
   */
  private Map<String, ValidationCodeType> urlMap = new HashMap<>();

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

    // image code validation is required on login
    urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidationCodeType.IMAGE);
    addUrlToMap(securityProperties.getValidation().getImage().getUrl(), ValidationCodeType.IMAGE);

    addUrlToMap(securityProperties.getValidation().getSms().getUrl(), ValidationCodeType.SMS);
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

    ValidationCodeType type = getValidationCodeType(request);
    if (type != null) {
      logger.info("Validation type " + type + " required for " + request.getRequestURI());

      ValidationCodeProcessor validationCodeProcessor =
          validationCodeProcessors.get(
              type.toString().toLowerCase() + ValidationCodeProcessor.class.getSimpleName());
      try {
        validationCodeProcessor.validate(new ServletWebRequest(request, response));
        logger.info("Validation passed.");
      } catch (ValidationCodeException exception) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private void addUrlToMap(String urlString, ValidationCodeType type) {
    if (StringUtils.isNotBlank(urlString)) {
      String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
      for (String url : urls) {
        urlMap.put(url, type);
      }
    }
  }

  private ValidationCodeType getValidationCodeType(HttpServletRequest request) {
    ValidationCodeType result = null;

    logger.info("request method " + request.getMethod());
    logger.info("request uri " + request.getRequestURI());
    if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
      Set<String> urls = urlMap.keySet();
      for (String url : urls) {
        if (pathMatcher.match(url, request.getRequestURI())) {
          result = urlMap.get(url);
        }
      }
    }

    return result;
  }
}
