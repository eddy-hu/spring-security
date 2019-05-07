package org.thecodeschool.security.core.validate.code;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.thecodeschool.security.core.properties.SecurityProperties;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
		
	@Autowired
	private SecurityProperties securityProperties;

	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.random(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code,securityProperties.getCode().getSms().getExpireIn());
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
	

}
