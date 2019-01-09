/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package boot.security.demo;

import boot.security.app.AppApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
// @Import({BrowserApplication.class})
@Import({AppApplication.class})
public class DemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello Spring Security";
  }
}
