package boot.security.app.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  /**
   * Get current principal.
   *
   * @param principal
   * @return
   */
  @GetMapping("/me")
  public Object getCurrentUser(@AuthenticationPrincipal UserDetails principal) {
    return principal;
  }
}
