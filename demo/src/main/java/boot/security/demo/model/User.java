package boot.security.demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

public class User {

  @JsonView(UserSimpleView.class)
  private String id;

  @JsonView(UserSimpleView.class)
  private String username;

  @NotBlank
  // @NonNull
  @JsonView(UserDetailView.class)
  private String password;

  @JsonView(UserSimpleView.class)
  @Past
  private Date birthday;

  public interface UserSimpleView {};

  public interface UserDetailView extends UserSimpleView {};

  public User() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
}
