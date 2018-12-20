package boot.security.core.validation;

import boot.security.core.validation.image.ImageCode;
import boot.security.core.validation.sms.SmsCodeSender;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationCodeController {

  public static final String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";
  public static final String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ValidationCodeGenerator<ImageCode> imageCodeGenerator;
  @Autowired private ValidationCodeGenerator<ValidationCode> smsCodeGenerator;
  @Autowired private SmsCodeSender smsCodeSender;

  @GetMapping(value = "/code/image", produces = "image/jpeg")
  public void createCodeImage(HttpServletRequest request, HttpServletResponse response) {
    // Generate a ImageCode
    ImageCode imageCode = imageCodeGenerator.generate(request);
    // Store the ImageCode in the session
    // Response the ImageCode to the client
    HttpSession session = request.getSession();

    session.setAttribute(SESSION_KEY_IMAGE_CODE, imageCode);
    logger.info(
        "ImageCode - " + ((ImageCode) session.getAttribute(SESSION_KEY_IMAGE_CODE)).getCode());
    try {
      ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @GetMapping(value = "/code/sms")
  public void createSmsCode(HttpServletRequest request) throws ServletRequestBindingException {
    String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
    // Generate a ImageCode
    ValidationCode smsCode = smsCodeGenerator.generate(request);
    // Store the ValidationCode in the session
    // Response the ValidationCode to the client
    HttpSession session = request.getSession();

    session.setAttribute(SESSION_KEY_SMS_CODE, smsCode);
    logger.info(
        "SmsCode - " + ((ValidationCode) session.getAttribute(SESSION_KEY_SMS_CODE)).getCode());
    smsCodeSender.send(mobile, smsCode.getCode());
  }
}
