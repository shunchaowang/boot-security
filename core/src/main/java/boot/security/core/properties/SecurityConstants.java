package boot.security.core.properties;

public interface SecurityConstants {

  // default url prefix for validation
  String DEFAULT_VALIDATION_CODE_URL_PREFIX = "/code";

  // default url for authentication required
  String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

  // login url
  String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

  /** 默认的手机验证码登录请求处理url */
  String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";
  /**
   * 默认登录页面
   *
   * @see SecurityController
   */
  String DEFAULT_LOGIN_PAGE_URL = "/boot-login.html";
  /** 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称 */
  String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
  /** 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称 */
  String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
  /** 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称 */
  String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
  /** session失效默认的跳转地址 */
  String DEFAULT_SESSION_INVALID_URL = "/session/invalid";

  String MYSQL_DRIVER_KEY = "mysql.driver";
  String MYSQL_URL_KEY = "mysql.url";
  String MYSQL_USERNAME_KEY = "mysql.username";
  String MYSQL_PASSWORD_KEY = "mysql.password";
}
