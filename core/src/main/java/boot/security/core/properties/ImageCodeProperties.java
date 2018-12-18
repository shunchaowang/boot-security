package boot.security.core.properties;

public class ImageCodeProperties {

  private int width = 80;

  private int height = 40;

  private int length = 4;

  private int expiredIn = 60;

  private String url = "";

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

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getExpiredIn() {
    return expiredIn;
  }

  public void setExpiredIn(int expiredIn) {
    this.expiredIn = expiredIn;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
