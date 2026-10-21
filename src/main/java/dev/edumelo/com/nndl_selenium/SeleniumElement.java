package dev.edumelo.com.nndl_selenium;

import java.util.function.Supplier;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import dev.edumelo.com.nndl_core.webdriver.NndlElement;
import dev.edumelo.com.nndl_core.webdriver.NndlSearchContext;

final class SeleniumElement extends SeleniumSearchContext implements NndlElement {

	private final WebElement element;

	SeleniumElement(WebElement element) {
		super(element);
		this.element = element;
	}

	WebElement unwrap() {
		return element;
	}

	static WebElement unwrap(NndlElement element) {
		if (element instanceof SeleniumElement seleniumElement) {
			return seleniumElement.unwrap();
		}
		throw new IllegalArgumentException("Element does not come from the selenium provider");
	}

	@Override
	public void click() {
		run(element::click);
	}

	@Override
	public void sendText(CharSequence text) {
		run(() -> element.sendKeys(text));
	}

	@Override
	public void sendChord(String... parts) {
		run(() -> element.sendKeys(SeleniumKeys.chord(parts)));
	}

	@Override
	public int getX() {
		return read(() -> element.getLocation().getX());
	}

	@Override
	public int getY() {
		return read(() -> element.getRect().getY());
	}

	@Override
	public boolean isDisplayed() {
		return read(element::isDisplayed);
	}

	@Override
	public boolean isEnabled() {
		return read(element::isEnabled);
	}

	@Override
	public NndlSearchContext shadowRoot() {
		return read(() -> new SeleniumSearchContext(element.getShadowRoot()));
	}

	private void run(Runnable action) {
		try {
			action.run();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	private <T> T read(Supplier<T> action) {
		try {
			return action.get();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

}
