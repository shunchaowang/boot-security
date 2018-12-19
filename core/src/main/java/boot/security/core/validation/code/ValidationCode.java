package boot.security.core.validation.code;

import java.time.LocalDateTime;

public class ValidationCode {

  private String code;

  private LocalDateTime expirationTime;

  public ValidationCode(String code, LocalDateTime expirationTime) {
    this.code = code;
    this.expirationTime = expirationTime;
  }

  public ValidationCode(String code, int expiredIn) {
    this.code = code;
    this.expirationTime = LocalDateTime.now().plusSeconds(expiredIn);
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
