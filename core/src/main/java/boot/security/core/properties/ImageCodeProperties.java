package boot.security.core.properties;

public class ImageCodeProperties extends SmsCodeProperties {

  public ImageCodeProperties() {
    setLength(4);
  }

  private int width = 80;

  private int height = 40;

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
