package boot.security.web.demo.validation;

import boot.security.core.validation.ValidationCodeGenerator;
import boot.security.core.validation.image.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

// rename this ben to be imageCodeGenerator if you want to override the generator from core
@Component("demoImageCodeGenerator")
public class DemoValidationCodeGenerator implements ValidationCodeGenerator<ImageCode> {

  @Override
  public ImageCode generate(ServletWebRequest request) {
    System.out.println("ImageCodeGenerator from Demo");
    return null;
  }
}
