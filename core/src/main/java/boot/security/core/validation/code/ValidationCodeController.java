package boot.security.core.validation.code;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationCodeController {

  public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ValidationCodeGenerator<ImageCode> imageCodeGenerator;

  @GetMapping(value = "/code/image", produces = "image/jpeg")
  public void createCodeImage(HttpServletRequest request, HttpServletResponse response) {
    // Generate a ImageCode
    ImageCode imageCode = imageCodeGenerator.generate(request);
    // Store the ImageCode in the session
    // Response the ImageCode to the client
    HttpSession session = request.getSession();

    session.setAttribute(SESSION_KEY, imageCode);
    logger.info(((ImageCode) session.getAttribute(SESSION_KEY)).getCode());
    try {
      ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
