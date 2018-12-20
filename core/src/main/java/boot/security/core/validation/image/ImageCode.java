package boot.security.core.validation.image;

import boot.security.core.validation.ValidationCode;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ImageCode extends ValidationCode implements Serializable {

  private static final long serialVersionUID = 1L;

  private BufferedImage image;

  public ImageCode(BufferedImage image, String code, LocalDateTime expirationTime) {
    super(code, expirationTime);
    this.image = image;
  }

  public ImageCode(BufferedImage image, String code, int expiredIn) {
    super(code, expiredIn);
    this.image = image;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
