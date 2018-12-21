package boot.security.core.validation;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidationCodeGenerator<T extends ValidationCode> {

  T generate(ServletWebRequest request);
}
