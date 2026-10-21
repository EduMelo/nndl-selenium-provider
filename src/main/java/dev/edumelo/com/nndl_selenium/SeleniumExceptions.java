package dev.edumelo.com.nndl_selenium;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;

import dev.edumelo.com.nndl_core.exceptions.unchecked.NndlDriverException;
import dev.edumelo.com.nndl_core.exceptions.unchecked.NndlElementNotInteractableException;
import dev.edumelo.com.nndl_core.exceptions.unchecked.NndlNotFoundException;
import dev.edumelo.com.nndl_core.exceptions.unchecked.NndlStaleElementException;
import dev.edumelo.com.nndl_core.exceptions.unchecked.NndlTimeoutException;

final class SeleniumExceptions {

	private SeleniumExceptions() {
	}

	static RuntimeException translate(WebDriverException exception) {
		if (exception instanceof ElementNotInteractableException) {
			return new NndlElementNotInteractableException(exception.getMessage(), exception);
		}
		if (exception instanceof StaleElementReferenceException) {
			return new NndlStaleElementException(exception.getMessage(), exception);
		}
		if (exception instanceof TimeoutException) {
			return new NndlTimeoutException(exception.getMessage(), exception);
		}
		if (exception instanceof NotFoundException) {
			return new NndlNotFoundException(exception.getMessage(), exception);
		}
		return new NndlDriverException(exception.getMessage(), exception);
	}

}
