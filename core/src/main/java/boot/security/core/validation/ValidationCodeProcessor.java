package boot.security.core.validation;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidationCodeProcessor {

  String SESSION_KEY_PREFIX = "SESSION_KEY_CODE_";

  void create(ServletWebRequest request);

  void validate(ServletWebRequest request);
}
