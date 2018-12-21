package boot.security.core.validation.image;

import boot.security.core.validation.AbstractValidationCodeProcessor;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("imageValidationCodeProcessor")
public class ImageValidationCodeProcessor extends AbstractValidationCodeProcessor<ImageCode> {

  @Override
  protected void send(ServletWebRequest request, ImageCode validationCode) {
    HttpServletResponse response = request.getResponse();

    try {
      ImageIO.write(validationCode.getImage(), "JPEG", response.getOutputStream());
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
