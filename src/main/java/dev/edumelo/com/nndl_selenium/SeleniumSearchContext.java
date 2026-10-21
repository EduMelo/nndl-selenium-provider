package dev.edumelo.com.nndl_selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;

import dev.edumelo.com.nndl_core.webdriver.NndlElement;
import dev.edumelo.com.nndl_core.webdriver.NndlLocator;
import dev.edumelo.com.nndl_core.webdriver.NndlSearchContext;

class SeleniumSearchContext implements NndlSearchContext {

	private final SearchContext context;

	SeleniumSearchContext(SearchContext context) {
		this.context = context;
	}

	@Override
	public NndlElement find(NndlLocator locator) {
		try {
			return new SeleniumElement(context.findElement(toBy(locator)));
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	@Override
	public List<NndlElement> findAll(NndlLocator locator) {
		try {
			return context.findElements(toBy(locator)).stream()
					.map(SeleniumElement::new)
					.map(element -> (NndlElement) element)
					.toList();
		} catch (WebDriverException e) {
			throw SeleniumExceptions.translate(e);
		}
	}

	static By toBy(NndlLocator locator) {
		return switch (locator.getKind()) {
			case CSS -> By.cssSelector(locator.getValue());
			case TAG -> By.tagName(locator.getValue());
		};
	}

}
