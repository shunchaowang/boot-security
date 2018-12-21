package boot.security.core.validation;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ValidationCodeController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private Map<String, ValidationCodeProcessor> validationCodeProcessors;

  @GetMapping(value = "/code/{type}")
  public void createCode(
      HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
    validationCodeProcessors
        .get(type + ValidationCodeProcessor.class.getSimpleName())
        .create(new ServletWebRequest(request, response));
  }
}
