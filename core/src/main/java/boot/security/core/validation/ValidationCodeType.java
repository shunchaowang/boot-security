package boot.security.core.validation;

import boot.security.core.properties.SecurityConstants;

public enum ValidationCodeType {
  SMS {
    @Override
    public String getParamNameOnValidation() {
      return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
    }
  },

  IMAGE {
    @Override
    public String getParamNameOnValidation() {
      return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
    }
  };

  public abstract String getParamNameOnValidation();
}
