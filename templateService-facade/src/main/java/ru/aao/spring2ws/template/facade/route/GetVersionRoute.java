package ru.aao.spring2ws.template.facade.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.facade.service.TemplateService;

/**
 * @author Alyanov Artem 14.12.2022
 */
@Component
@RequiredArgsConstructor
public class GetVersionRoute extends RouteBuilder {

    private final TemplateService service;

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("direct:getVersion")
            .routeId("getVersionRouteId")
                .to("direct:beforeImplementationRoute")
                .log(LoggingLevel.INFO, "${header." + Constant.CORRELATION_ID + "}")
                .bean(service, "getVersion()")
                .to("direct:afterImplementationRoute")
        .end();

    }
}
