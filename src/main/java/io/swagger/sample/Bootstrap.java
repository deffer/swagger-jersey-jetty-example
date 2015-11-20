package io.swagger.sample;

import io.swagger.config.ScannerFactory;
import io.swagger.models.*;
import io.swagger.jaxrs.config.ReflectiveJaxrsScanner;

import io.swagger.models.auth.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Bootstrap extends HttpServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
    scanner.setResourcePackage("io.swagger.sample.resource");
    ScannerFactory.setScanner(scanner);
    Info info = new Info()
      .title("Person API")
      .version("1.0.0")
      .description("This is a sample Person API mixed with example Petstore API from Swagger.  You can find out more about Swagger " +
        "at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, " +
        "you can use the api key `special-key` to test the authorization filters.")
      .termsOfService("http://swagger.io/terms/")
      .contact(new Contact()
        .email("apiteam@auckland.ac.nz"))
      .license(new License()
        .name("Apache 2.0")
        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

    ServletContext context = config.getServletContext();
    Swagger swagger = new Swagger()
      .info(info)
      //.host("localhost:8002")
      .basePath(context.getContextPath() + "/api");
    swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
    swagger.securityDefinition("petstore_auth",
            new OAuth2Definition()
                    .implicit("http://petstore.swagger.io/api/oauth/dialog")
                    .scope("read:pets", "read your pets")
                    .scope("write:pets", "modify pets in your account"));
    swagger.securityDefinition("pp_oauth",
            new OAuth2Definition()
                    .implicit("http://pam.dev.auckland.ac.nz/oauth/authorize")
                    .scope("person:read", "read person information")
                    .scope("person:write", "update person details"));
      swagger.securityDefinition("pp_basic",
            new BasicAuthDefinition());
    swagger.tag(new Tag().name("person").description("Operations on person"));
    swagger.tag(new Tag().name("secrets").description("API configuration, etc"));
    swagger.tag(new Tag()
            .name("user").description("Swagger original")
            .externalDocs(new ExternalDocs("Find out more about our store", "http://swagger.io")));
    swagger.tag(new Tag()
            .name("pet").description("Swagger original").externalDocs(new ExternalDocs("Find out more about our store", "http://swagger.io")));
    swagger.tag(new Tag().name("store") .description("Swagger original")
            .externalDocs(new ExternalDocs("Find out more about our store", "http://swagger.io")));
    context.setAttribute("swagger", swagger);
  }
}
