package boot.security.core.validation;

import javax.servlet.http.HttpServletRequest;

public interface ValidationCodeGenerator<T extends ValidationCode> {

  T generate(HttpServletRequest request);
}
