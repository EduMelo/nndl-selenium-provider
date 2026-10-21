package dev.edumelo.com.nndl_selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import dev.edumelo.com.nndl_core.webdriver.NndlCookie;

class SeleniumCookiesTest {

	@Test
	void cookieRoundTripKeepsTheFieldsSeleniumStores() {
		Date expiry = new Date(1_700_000_000_000L);
		Cookie original = new Cookie("session", "abc", "example.com", "/", expiry, true, true, "Lax");

		NndlCookie converted = SeleniumCookies.fromSelenium(original);
		Cookie restored = SeleniumCookies.toSelenium(converted);

		assertThat(restored.getName()).isEqualTo("session");
		assertThat(restored.getValue()).isEqualTo("abc");
		assertThat(restored.getDomain()).isEqualTo("example.com");
		assertThat(restored.getPath()).isEqualTo("/");
		assertThat(restored.getExpiry()).isEqualTo(expiry);
		assertThat(restored.isSecure()).isTrue();
		assertThat(restored.isHttpOnly()).isTrue();
		assertThat(restored.getSameSite()).isEqualTo("Lax");
	}

}
