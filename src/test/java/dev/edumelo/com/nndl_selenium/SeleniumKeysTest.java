package dev.edumelo.com.nndl_selenium;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

class SeleniumKeysTest {

	@Test
	void namedKeyBecomesTheSeleniumKey() {
		assertThat(SeleniumKeys.sequence("ENTER")).isEqualTo(Keys.ENTER);
	}

	@Test
	void unknownTextStaysText() {
		assertThat(SeleniumKeys.sequence("not-a-key")).isEqualTo("not-a-key");
	}

	@Test
	void chordMixesNamedKeysAndText() {
		assertThat(SeleniumKeys.chord("CONTROL", "a", "DELETE"))
				.isEqualTo(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	}

}
