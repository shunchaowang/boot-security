package boot.security.core.validation.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletWebRequest;

public class SmsCodeFilter extends ValadationCodeFilter {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public void validate(HttpServletRequest request) throws ValidationCodeException {

    HttpSession session = request.getSession();
    ValidationCode validationInSession =
        (ValidationCode) session.getAttribute(ValidationCodeController.SESSION_KEY_SMS_CODE);
    ServletWebRequest webRequest = new ServletWebRequest(request);
    String codeInRequest = webRequest.getParameter("smsCode");

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

    session.removeAttribute(ValidationCodeController.SESSION_KEY_SMS_CODE);
  }
}
