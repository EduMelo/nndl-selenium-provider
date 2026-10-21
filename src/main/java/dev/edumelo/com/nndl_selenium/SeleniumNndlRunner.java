package dev.edumelo.com.nndl_selenium;

import dev.edumelo.com.nndl_core.NndlRunner;
import dev.edumelo.com.nndl_core.webdriver.SeleniumHubProperties;

public class SeleniumNndlRunner extends NndlRunner {

	public SeleniumNndlRunner(SeleniumHubProperties properties) {
		super(new SeleniumBrowserSession(properties));
	}

}
