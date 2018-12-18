package boot.security.core.validation.code;

import javax.servlet.http.HttpServletRequest;

public interface ValidationCodeGenerator<T> {

  T generate(HttpServletRequest request);
}
