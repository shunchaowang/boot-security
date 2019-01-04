package boot.security.core.validation;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

public abstract class AbstractValidationCodeProcessor<C extends ValidationCode>
    implements ValidationCodeProcessor {

  /**
   * Spring Bean Scan, will store all implementations of validationCodeGenerator into a class simple
   * name mapped to the implementation class. e.g.
   * validationCodeGenerators["imagevalidationCodeGenerator"] = ImageValidationCodeGenerator.class
   * validationCodeGenerators["smsvalidationCodeGenerator"] = SmsValidationCodeGenerator
   */
  @Autowired private Map<String, ValidationCodeGenerator> validationCodeGenerators;

  @Override
  public void create(ServletWebRequest request) throws ValidationCodeException {
    C validationCode = generate(request);
    save(request, validationCode);
    send(request, validationCode);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void validate(ServletWebRequest request) {
    ValidationCodeType type = getValidationCodeType();
    String sessionKey = getSessionKey();

    HttpSession session = request.getRequest().getSession();
    ValidationCode codeInSession = (ValidationCode) session.getAttribute(sessionKey);

    //    ServletWebRequest webRequest = new ServletWebRequest(request);
    String codeInRequest;

    try {
      codeInRequest =
          ServletRequestUtils.getStringParameter(
              request.getRequest(), type.getParamNameOnValidation());
    } catch (ServletRequestBindingException e) {
      throw new ValidationCodeException("Error retrieving param from request.");
    }

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidationCodeException("Code Not Exist in Request.");
    }

    if (codeInSession == null) {
      throw new ValidationCodeException("Code Not Exist in Session.");
    }

    if (codeInSession.isExpired()) {
      throw new ValidationCodeException("Code Expired in Session.");
    }

    if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
      throw new ValidationCodeException("Code Not Matched.");
    }

    session.removeAttribute(sessionKey);
  }

  @SuppressWarnings("unchecked")
  private C generate(ServletWebRequest request) {
    String type = getValidationCodeType().toString().toLowerCase();
    String generatorName = type + ValidationCodeGenerator.class.getSimpleName();
    ValidationCodeGenerator generator = validationCodeGenerators.get(generatorName);
    if (generator == null) {
      throw new ValidationCodeException(
          "Validation Code Generator " + generatorName + " does not exist");
    }
    return (C) generator.generate(request);
  }

  private void save(ServletWebRequest request, C validationCode) {
    HttpSession session = request.getRequest().getSession();
    // Image is not needed to stored,
    // so we create another ValidationCode which can be serializable
    ValidationCode code =
        new ValidationCode(validationCode.getCode(), validationCode.getExpirationTime());

    session.setAttribute(getSessionKey(), code);
  }

  private String getSessionKey() {
    return SESSION_KEY_PREFIX + getValidationCodeType().toString().toUpperCase();
  }

  private ValidationCodeType getValidationCodeType() {
    String type =
        StringUtils.substringBefore(
            getClass().getSimpleName(), ValidationCodeProcessor.class.getSimpleName());
    return ValidationCodeType.valueOf(type.toUpperCase());
  }

  protected abstract void send(ServletWebRequest request, C validationCode);
}
