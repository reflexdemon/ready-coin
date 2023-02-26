package io.vpv.readycoin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Ready Coin Change",
                description = "" +
                        "This is a simple API that can build give change to bills",
                contact = @Contact(
                        name = "ready-coin",
                        url = "https://vpv.io",
                        email = "contact@vpv.io"
                ),
                license = @License(
                        name = "MIT Licence",
                        url = "https://github.com/thombergs/code-examples/blob/master/LICENSE")),
        servers = @Server(url = "http://localhost:8080")
)
public class APIDocConfig {
}
