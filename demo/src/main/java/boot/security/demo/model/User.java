package boot.security.demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @JsonView(UserSimpleView.class)
  private String username;

  @JsonView(UserDetailView.class)
  private String password;

  public interface UserSimpleView {};

  public interface UserDetailView extends UserSimpleView {};
}
