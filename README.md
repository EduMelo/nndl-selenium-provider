# nndl-selenium-provider

Implementação Selenium das interfaces de driver do [nndl-core](https://github.com/EduMelo/nndl-core).

O core descreve a sessão, o elemento e a espera. Este projeto abre o `RemoteWebDriver` e executa essas operações.

Requer Java 25, `dev.edumelo:nndl-core:0.0.2-SNAPSHOT` e `selenium-server` 4.21.0.

```java
SeleniumHubProperties properties = new SeleniumHubProperties();
properties.setHost("localhost");
properties.setPort(4444);

new SeleniumNndlRunner(properties).runFromDhall(Path.of("recipe.dhall"), adapters);
```
