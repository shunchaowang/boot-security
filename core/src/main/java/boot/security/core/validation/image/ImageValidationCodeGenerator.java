package boot.security.core.validation.image;

import boot.security.core.properties.SecurityProperties;
import boot.security.core.validation.ValidationCodeGenerator;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component("imageValidationCodeGenerator")
public class ImageValidationCodeGenerator implements ValidationCodeGenerator<ImageCode> {

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public ImageCode generate(ServletWebRequest request) {
    int length = securityProperties.getValidation().getImage().getLength();

    int width =
        ServletRequestUtils.getIntParameter(
            request.getRequest(), "width", securityProperties.getValidation().getImage().getWidth());
    int height =
        ServletRequestUtils.getIntParameter(
            request.getRequest(), "height", securityProperties.getValidation().getImage().getHeight());

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

    return new ImageCode(
        image, code.toString(), securityProperties.getValidation().getImage().getExpiredIn());
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
