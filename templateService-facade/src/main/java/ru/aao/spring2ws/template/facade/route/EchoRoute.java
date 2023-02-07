package ru.aao.spring2ws.template.facade.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.facade.service.TemplateService;

/**
* @author Alyanov Artem 21.11.2022
*/

@Component
@RequiredArgsConstructor
public class EchoRoute extends RouteBuilder {

    private final TemplateService service;

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("direct:echo")
                .routeId(Constant.NAMEPROJECT+"Echo")
                .setProperty(Constant.LOG_COMPONENT, constant(Constant.NAMEPROJECT+"Echo"))
                .to("direct:beforeImplementationRoute")

                .log(LoggingLevel.INFO, "${header." + Constant.CORRELATION_ID + "}")
                .bean(service, "echo(${body})")

                .to("direct:afterImplementationRoute")
        .end();
    }
}
