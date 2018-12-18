package boot.security.core.validation.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationCodeController {

  public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @GetMapping(value = "/code/image", produces = "image/jpeg")
  public void createCodeImage(HttpServletRequest request, HttpServletResponse response) {
    // Generate a ImageCode
    ImageCode imageCode = createImageCode();
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

  private ImageCode createImageCode() {

    int width = 90, height = 40, length = 4;
    int xx = 15, fontHeight = 35, yy = 30;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = image.getGraphics();

    // create a random number
    Random random = new Random();

    // file the image with while
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, width, height);

    // create font
    Font font = new Font("Times New Roman", Font.BOLD, fontHeight);
    graphics.setFont(font);

    // shade
    graphics.setColor(Color.BLACK);
    graphics.drawRect(0, 0, width - 1, height - 1);

    // randomly generate 40 lines
    graphics.setColor(Color.BLACK);
    for (int i = 0; i < 20; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int xl = random.nextInt(12);
      int yl = random.nextInt(12);
      graphics.drawLine(x, y, x + xl, y + yl);
    }

    // generate random code of count
    StringBuffer code = new StringBuffer();

    // generate random color for different number
    int red = 0, green = 0, blue = 0;
    for (int i = 0; i < length; i++) {
      String s = String.valueOf(random.nextInt(10));
      red = random.nextInt(255);
      green = random.nextInt(255);
      blue = random.nextInt(255);

      // draw the generate string into the image using the color
      graphics.setColor(new Color(red, green, blue));
      graphics.drawString(s, xx * (i + 1), yy);
      code.append(s);
    }

    graphics.dispose();

    return new ImageCode(image, code.toString(), 60);
  }
}
