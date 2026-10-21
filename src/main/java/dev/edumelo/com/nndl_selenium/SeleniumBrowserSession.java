package dev.edumelo.com.nndl_selenium;

import dev.edumelo.com.nndl_core.contextAdapter.NndlContextManager;
import dev.edumelo.com.nndl_core.webdriver.NndlBrowserSession;
import dev.edumelo.com.nndl_core.webdriver.NndlWebDriver;
import dev.edumelo.com.nndl_core.webdriver.NndlWebDriverWaiter;
import dev.edumelo.com.nndl_core.webdriver.SeleniumHubProperties;

public final class SeleniumBrowserSession implements NndlBrowserSession {

	private final BrowserControllerDriverConfiguration configuration;

	public SeleniumBrowserSession(SeleniumHubProperties properties) {
		this.configuration = new BrowserControllerDriverConfiguration(properties);
	}

	@Override
	public SeleniumHubProperties properties() {
		return configuration.getProperties();
	}

	@Override
	public NndlWebDriver open() {
		SeleniumNndlWebDriver driver = new SeleniumNndlWebDriver(configuration,
				NndlContextManager.getBrowserArgumentsContextAdapter());
		driver.open();
		return driver;
	}

	@Override
	public NndlWebDriverWaiter waiter(NndlWebDriver driver) {
		return new SeleniumNndlWebDriverWaiter((SeleniumNndlWebDriver) driver);
	}

}
