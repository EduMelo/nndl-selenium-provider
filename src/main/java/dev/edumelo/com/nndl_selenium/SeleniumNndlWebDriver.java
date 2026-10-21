package dev.edumelo.com.nndl_selenium;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.edumelo.com.nndl_core.webdriver.BrowserArgumentsContextAdapter;
import dev.edumelo.com.nndl_core.webdriver.NndlCookie;
import dev.edumelo.com.nndl_core.webdriver.NndlElement;
import dev.edumelo.com.nndl_core.webdriver.NndlLocator;
import dev.edumelo.com.nndl_core.webdriver.NndlWebDriver;

public class SeleniumNndlWebDriver implements NndlWebDriver {

	private static final Logger log = LoggerFactory.getLogger(SeleniumNndlWebDriver.class);

	private final BrowserControllerDriverConfiguration browserConfiguration;
	private final List<BrowserArgumentsContextAdapter> browserArgumentsContextAdapter;
	private RemoteWebDriver webDriver;
	private SeleniumSearchContext search;
	private SessionId sessionId;

	public SeleniumNndlWebDriver(BrowserControllerDriverConfiguration browserConfiguration,
			List<BrowserArgumentsContextAdapter> browserArgumentsContextAdapter) {
		this.browserConfiguration = browserConfiguration;
		this.browserArgumentsContextAdapter = browserArgumentsContextAdapter;
	}

	public BrowserControllerDriverConfiguration getBrowserConfiguration() {
		return browserConfiguration;
	}

	RemoteWebDriver remote() {
		return webDriver;
	}

	@Override
	public void open() {
		try {
			webDriver = browserConfiguration.createRemoteDriver(browserArgumentsContextAdapter);
			sessionId = webDriver.getSessionId();
			search = new SeleniumSearchContext(webDriver);
		} catch (MalformedURLException e) {
			String msg = "Mal formed URL while creating web driver";
			log.error(msg);
			throw new RuntimeException(msg, e);
		}
	}

	@Override
	public void quit() {
		try {
			if (this.webDriver != null) {
				this.webDriver.quit();
			}
		} catch (WebDriverException e) {
			log.error("It wasn't possible to quit the webDriver with sessonId: " + sessionId);
		}
	}

	@Override
	public String getSessionId() {
		return sessionId.toString();
	}

	@Override
	public String getPageSource() {
		try {
			return this.webDriver.getPageSource();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public void get(String url) {
		try {
			webDriver.get(url);
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public String getCurrentUrl() {
		try {
			return webDriver.getCurrentUrl();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public void refresh() {
		try {
			webDriver.navigate().refresh();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public NndlElement activeElement() {
		try {
			return new SeleniumElement(webDriver.switchTo().activeElement());
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public Object executeScript(String script, Object... args) {
		try {
			return webDriver.executeScript(script, unwrap(args));
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public List<String> getWindowHandles() {
		try {
			return new ArrayList<>(webDriver.getWindowHandles());
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public void switchToWindow(String handle) {
		try {
			webDriver.switchTo().window(handle);
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public void closeWindow() {
		try {
			webDriver.close();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public Set<NndlCookie> getCookies() {
		try {
			return webDriver.manage().getCookies().stream()
					.map(SeleniumCookies::fromSelenium)
					.collect(Collectors.toSet());
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public void addCookie(NndlCookie cookie) {
		try {
			webDriver.manage().addCookie(SeleniumCookies.toSelenium(cookie));
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public void hover(NndlElement element) {
		try {
			new Actions(webDriver).moveToElement(SeleniumElement.unwrap(element)).perform();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public NndlElement find(NndlLocator locator) {
		return search.find(locator);
	}

	@Override
	public List<NndlElement> findAll(NndlLocator locator) {
		return search.findAll(locator);
	}

	private Object[] unwrap(Object... args) {
		Object[] unwrapped = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			unwrapped[i] = args[i] instanceof SeleniumElement element ? element.unwrap() : args[i];
		}
		return unwrapped;
	}

}
