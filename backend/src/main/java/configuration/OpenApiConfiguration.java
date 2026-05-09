package configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

@OpenAPIDefinition(
    info = @Info(
        title = "JYAA Swagger API",
        version = "1.0.0",
        description = "Documentación de la API JYAA",
        contact = @Contact(
            name = "teo-felix",
            email = "teo-felix@gmail.com"
        )
    ),
    servers = {
        @Server(
            description = "Servidor local de desarrollo",
            url = "http://localhost:8080/Entrega4/rest"
        ),
        @Server(
                description = "Servidor local de desarrollo",
                url = "https://grupo8.jyaa-ci.linti.unlp.edu.ar/rest"
            )
    }
)
public class OpenApiConfiguration {

}
