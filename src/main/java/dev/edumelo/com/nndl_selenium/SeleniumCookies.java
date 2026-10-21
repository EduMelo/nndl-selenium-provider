package dev.edumelo.com.nndl_selenium;

import org.openqa.selenium.Cookie;

import dev.edumelo.com.nndl_core.webdriver.NndlCookie;

final class SeleniumCookies {

	private SeleniumCookies() {
	}

	static NndlCookie fromSelenium(Cookie cookie) {
		return new NndlCookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(),
				cookie.getExpiry(), cookie.isSecure(), cookie.isHttpOnly(), cookie.getSameSite());
	}

	static Cookie toSelenium(NndlCookie cookie) {
		return new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(),
				cookie.getExpiry(), cookie.isSecure(), cookie.isHttpOnly(), cookie.getSameSite());
	}

}
