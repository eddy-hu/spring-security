package org.thecodeschool.security.core.validate.code;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.thecodeschool.security.core.validate.code.sms.SmsCodeSender;

@RestController
public class ValidateCodeController {
	
	static final String IMAGE_SESSION_KEY = "SESSIONKEY_IMAGE_CODE";
	static final String MOBILE_SESSION_KEY = "SESSIONKEY_MOBILE_CODE";
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;

	@Autowired
	private ValidateCodeGenerator smsCodeGenerator;
	
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
		sessionStrategy.setAttribute(new ServletWebRequest(request), IMAGE_SESSION_KEY, imageCode);
		ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
	}
	
	@GetMapping("/code/sms")
	public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
		ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
		sessionStrategy.setAttribute(new ServletWebRequest(request), MOBILE_SESSION_KEY, smsCode);
		String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
		smsCodeSender.send(mobile, smsCode.getCode());
	}
	

}
