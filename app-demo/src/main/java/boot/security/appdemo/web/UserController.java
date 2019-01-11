package boot.security.appdemo.web;

import boot.security.appdemo.model.User;
import boot.security.appdemo.model.UserQueryCriteria;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @GetMapping
  @JsonView(User.UserSimpleView.class)
  //  public List<User> query(@RequestParam String username) {
  public List<User> query(UserQueryCriteria criteria, Pageable pageable) {

    logger.info(ReflectionToStringBuilder.toString(criteria, ToStringStyle.MULTI_LINE_STYLE));
    logger.info(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      users.add(new User());
    }
    return users;
  }

  @GetMapping("/{id:\\d+}")
  @JsonView(User.UserDetailView.class)
  public User getInfo(@PathVariable String id) {
    //    throw new UserNotExistException(id);
    User user = new User();
    user.setUsername("tom");
    return user;
  }

  @PostMapping
  public User create(@RequestBody @Valid User user, BindingResult errors) {

    if (errors.hasErrors()) {
      errors.getAllErrors().stream().forEach(error -> System.out.println(error));
    }

    logger.info(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
    user.setId("1");
    return user;
  }

  @PutMapping("/{id:\\d+}")
  public User update(@RequestBody @Valid User user, BindingResult errors) {

    if (errors.hasErrors()) {
      errors
          .getAllErrors()
          .stream()
          .forEach(
              error -> {
                FieldError fieldError = (FieldError) error;
                System.out.println(fieldError.getField() + " " + fieldError.getDefaultMessage());
              });
    }

    logger.info(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
    user.setId("1");
    return user;
  }

  @DeleteMapping("/{id:\\d+}")
  public void delete(@PathVariable String id) {
    System.out.println(id);
  }
}
