package dev.edumelo.com.nndl_selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.edumelo.com.nndl_core.exceptions.unchecked.NndlNotFoundException;
import dev.edumelo.com.nndl_core.webdriver.NndlElement;
import dev.edumelo.com.nndl_core.webdriver.NndlElementCondition;
import dev.edumelo.com.nndl_core.webdriver.NndlElementsCondition;
import dev.edumelo.com.nndl_core.webdriver.NndlWebDriverWaiter;

public class SeleniumNndlWebDriverWaiter implements NndlWebDriverWaiter {

	private final WebDriverWait webDriverWait;

	public SeleniumNndlWebDriverWaiter(SeleniumNndlWebDriver webDriver) {
		this.webDriverWait = webDriver.getBrowserConfiguration().createWait(webDriver.remote());
		this.webDriverWait.ignoring(NndlNotFoundException.class);
	}

	@Override
	public NndlElement untilElement(Duration timeout, NndlElementCondition condition) {
		try {
			return webDriverWait.withTimeout(timeout).until(driver -> condition.apply());
		} catch (TimeoutException e) {
			throw SeleniumExceptions.translate(e);
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public List<NndlElement> untilElements(Duration timeout, NndlElementsCondition condition) {
		try {
			return webDriverWait.withTimeout(timeout).until(driver -> condition.apply());
		} catch (TimeoutException e) {
			throw SeleniumExceptions.translate(e);
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public void untilScriptReturns(Duration timeout, String script) {
		try {
			webDriverWait.withTimeout(timeout).until(ExpectedConditions.jsReturnsValue(script));
		} catch (TimeoutException e) {
			throw SeleniumExceptions.translate(e);
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

}
