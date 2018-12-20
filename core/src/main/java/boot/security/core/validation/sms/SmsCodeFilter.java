package boot.security.core.validation.sms;

import boot.security.core.validation.ValadationCodeFilter;
import boot.security.core.validation.ValidationCode;
import boot.security.core.validation.ValidationCodeController;
import boot.security.core.validation.ValidationCodeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    validateRequestAndSession(codeInRequest, validationInSession);

    session.removeAttribute(ValidationCodeController.SESSION_KEY_SMS_CODE);
  }
}
