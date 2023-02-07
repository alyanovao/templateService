package ru.aao.spring2ws.template.facade.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.common.types.log.Direction;
import ru.aao.spring2ws.template.facade.processor.RestFaultResponseBuilder;
import ru.aao.spring2ws.template.facade.processor.WireTapProcessor;

@Component
public class ServiceRestFacade extends RouteBuilder {

    @Autowired
    private Environment env;

    @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private String port;

    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler());

        onException(Exception.class)
                .handled(true)
                .process(new RestFaultResponseBuilder())
                .wireTap("vm:logging")
                    .onPrepare(new WireTapProcessor(
                        exchangeProperty(Constant.LOG_COMPONENT),
                        constant("Exception"),
                        Direction.Inner,
                        exchangeProperty(Constant.LOG_STEP)
                    )
                )
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader("Content-Type", simple("${exchangeProperty.ContentType}"))
                .marshal().json(JsonLibrary.Jackson)
        .end();

        restConfiguration()
                .component("servlet")
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .host(env.getProperty("server.address", address))
                .port(env.getProperty("server.port", port))
                .contextPath(contextPath.substring(0, contextPath.length() - 2))
                //validation request
                .clientRequestValidation(true)
                // turn on openapi api-doc
                .apiContextPath("/swagger")
                .apiProperty("api.title", "User API")
                .apiProperty("api.version", "1.0.0");

        rest("").description("SmartSearch REST service")
                .id("api-route")
                .consumes("application/json")
                .produces("application/json")

                .post("/echo")
                .routeId("echo-api")
                .description("echo endpoint")
                .type(String.class)
                .outType(String.class)
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("direct:echo")

                .get("/getVersion")
                .routeId("getVersion-api")
                .description("getVersion endpoint")
                .outType(String.class)
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("direct:getVersion");
    }
}
