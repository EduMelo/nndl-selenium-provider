package dev.edumelo.com.nndl_selenium;

import org.openqa.selenium.Keys;

final class SeleniumKeys {

	private SeleniumKeys() {
	}

	static CharSequence sequence(String part) {
		try {
			return Keys.valueOf(part);
		} catch (IllegalArgumentException e) {
			return part;
		}
	}

	static CharSequence chord(String... parts) {
		CharSequence[] sequences = new CharSequence[parts.length];
		for (int i = 0; i < parts.length; i++) {
			sequences[i] = sequence(parts[i]);
		}
		return Keys.chord(sequences);
	}

}
