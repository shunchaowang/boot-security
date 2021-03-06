/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package boot.security.app;

import boot.security.core.CoreApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CoreApplication.class)
public class AppApplication {
  public static void main(String[] args) {
    System.out.println(new AppApplication().getGreeting());
  }

  public String getGreeting() {
    return "Hello world.";
  }
}
