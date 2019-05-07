package org.thecodeschool.security.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thecodeschool.security.core.properties.SecurityProperties;

public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

	private AuthenticationFailureHandler authenticationFailureHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	private Set<String> urls = new HashSet<>();

	private SecurityProperties securityProperties;

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] configUrls = StringUtils
				.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(), ",");
		if(ArrayUtils.isNotEmpty(configUrls)) {
			for (String configUrl : configUrls) {
				urls.add(configUrl);
			}			
		}
		urls.add("/authentication/mobile");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		boolean action = false;
		for (String url : urls) {
			if (pathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}

		if (action) {

			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}

		// else
		filterChain.doFilter(request, response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {

		ValidateCode code = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeController.MOBILE_SESSION_KEY);
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("Cannot be empty");
		}
		if (code.isExpired()) {
			sessionStrategy.removeAttribute(request, ValidateCodeController.MOBILE_SESSION_KEY);
			throw new ValidateCodeException("Code is expired");
		}
		if (!StringUtils.equals(codeInRequest, code.getCode())) {
			throw new ValidateCodeException("Did not matched");
		}

		sessionStrategy.removeAttribute(request, ValidateCodeController.MOBILE_SESSION_KEY);
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

}
