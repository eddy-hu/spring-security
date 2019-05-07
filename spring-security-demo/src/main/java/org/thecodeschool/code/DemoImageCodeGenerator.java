package org.thecodeschool.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.thecodeschool.security.core.validate.code.ImageCode;
import org.thecodeschool.security.core.validate.code.ValidateCodeGenerator;

//@Component("imageCodeGenerator") //for override default configuration
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	private Logger logger = LoggerFactory.getLogger(getClass());	

	@Override
	public ImageCode generate(ServletWebRequest request) {
		
		logger.info("Override imageCodeGenerator bean");
		return null;
	}
	
	

}
