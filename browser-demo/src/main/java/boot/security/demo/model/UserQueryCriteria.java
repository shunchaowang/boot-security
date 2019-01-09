package boot.security.demo.model;

public class UserQueryCriteria {

  private String username;

  private int age;

  private int ageTo;

  public UserQueryCriteria() {}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getAgeTo() {
    return ageTo;
  }

  public void setAgeTo(int ageTo) {
    this.ageTo = ageTo;
  }
}
