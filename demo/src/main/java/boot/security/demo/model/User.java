package boot.security.demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @JsonView(UserSimpleView.class)
  private String id;

  @JsonView(UserSimpleView.class)
  private String username;

  @NotBlank
  //@NonNull
  @JsonView(UserDetailView.class)
  private String password;

  @JsonView(UserSimpleView.class)
  private Date birthday;

  public interface UserSimpleView {};

  public interface UserDetailView extends UserSimpleView {};
}
