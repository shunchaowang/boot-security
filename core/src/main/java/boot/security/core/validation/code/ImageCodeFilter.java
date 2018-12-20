package boot.security.core.validation.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletWebRequest;

public class ImageCodeFilter extends ValadationCodeFilter {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public void validate(HttpServletRequest request) throws ValidationCodeException {

    HttpSession session = request.getSession();
    ImageCode validationInSession =
        (ImageCode) session.getAttribute(ValidationCodeController.SESSION_KEY_IMAGE_CODE);
    ServletWebRequest webRequest = new ServletWebRequest(request);
    String codeInRequest = webRequest.getParameter("imageCode");

    validateRequestAndSession(codeInRequest, validationInSession);

    session.removeAttribute(ValidationCodeController.SESSION_KEY_IMAGE_CODE);
  }
}
