package boot.security.demo.web;

import boot.security.demo.model.User;
import boot.security.demo.model.UserQueryCriteria;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @GetMapping()
//  public List<User> query(@RequestParam String username) {
  public List<User> query(UserQueryCriteria criteria) {

    logger.info(ReflectionToStringBuilder.toString(criteria, ToStringStyle.MULTI_LINE_STYLE));
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      users.add(new User());
    }
    return users;
  }
}
