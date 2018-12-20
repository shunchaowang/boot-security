package boot.security.demo.validation;

import boot.security.core.validation.image.ImageCode;
import boot.security.core.validation.ValidationCodeGenerator;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

// rename this ben to be imageCodeGenerator if you want to override the generator from core
@Component("demoImageCodeGenerator")
public class DemoValidationCodeGenerator implements ValidationCodeGenerator<ImageCode> {

  @Override
  public ImageCode generate(HttpServletRequest request) {
    System.out.println("ImageCodeGenerator from Demo");
    return null;
  }
}
