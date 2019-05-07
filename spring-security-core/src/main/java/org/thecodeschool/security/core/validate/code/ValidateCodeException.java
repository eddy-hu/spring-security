package org.thecodeschool.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5979163542038635189L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
