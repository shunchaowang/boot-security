package boot.security.core.properties;

public class ValidationCodeProperties {

  private ImageCodeProperties image = new ImageCodeProperties();

  private SmsCodeProperties sms = new SmsCodeProperties();

  public SmsCodeProperties getSms() {
    return sms;
  }

  public void setSms(SmsCodeProperties sms) {
    this.sms = sms;
  }

  public ImageCodeProperties getImage() {
    return image;
  }

  public void setImage(ImageCodeProperties image) {
    this.image = image;
  }
}
