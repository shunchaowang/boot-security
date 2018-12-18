package boot.security.core.validation.code;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ImageCode implements Serializable {

  private static final long serialVersionUID = 1L;

  private BufferedImage image;

  private String code;

  private LocalDateTime expirationTime;

  public ImageCode(BufferedImage image, String code, LocalDateTime expirationTime) {
    this.image = image;
    this.code = code;
    this.expirationTime = expirationTime;
  }

  public ImageCode(BufferedImage image, String code, int expiredIn) {
    this.image = image;
    this.code = code;
    this.expirationTime = LocalDateTime.now().plusSeconds(expiredIn);
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDateTime getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(LocalDateTime expirationTime) {
    this.expirationTime = expirationTime;
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expirationTime);
  }
}
