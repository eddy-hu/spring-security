package org.thecodeschool.security.core.properties;

public class BrowserProperties {
	
	private String loginPage = "/ss-login.html"; //default url if didn't set the value on properties
	
	

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	

}
