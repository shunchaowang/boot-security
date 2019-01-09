package boot.security.web.demo.web;

import boot.security.web.demo.UserNotExistException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(UserNotExistException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleUserNotException(UserNotExistException exception) {
    Map<String, Object> map = new HashMap<>();
    map.put("id", exception.getId());
    map.put("message", exception.getMessage());
    return map;
  }
}
