package org.thecodeschool.security.core.validate.code.sms;

public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		System.out.print("Send code:"+code+" to phone number:"+mobile);
	}

}
