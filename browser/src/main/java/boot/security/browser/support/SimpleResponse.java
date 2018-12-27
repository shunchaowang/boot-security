package boot.security.browser.support;

public class SimpleResponse<T> {

  private T content;

  public SimpleResponse(T content) {
    this.content = content;
  }

  public T getContent() {
    return content;
  }

  public void setContent(T content) {
    this.content = content;
  }
}
