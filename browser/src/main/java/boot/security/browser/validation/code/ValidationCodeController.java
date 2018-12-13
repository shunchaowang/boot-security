package boot.security.browser.validation.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationCodeController {

//  private SessionStrategy sessionStrategy;

  @GetMapping("/code/image")
  public void createCode(HttpServletRequest request, HttpServletResponse response) {
    // Generate a ImageCode
    // Store the ImageCode in the session
    // Response the ImageCode to the client
  }

  private ImageCode createImageCode(HttpServletRequest request) {

    return null;
  }
}
