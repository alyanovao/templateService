package ru.aao.spring2ws.template.facade.route.initialize;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.common.exception.ApplicationException;
import ru.aao.spring2ws.template.common.types.log.Direction;
import ru.aao.spring2ws.template.facade.processor.PrepareRequest;
import ru.aao.spring2ws.template.facade.processor.WireTapProcessor;
import ru.aao.spring2ws.template.logging.processor.LogIdGenerator;

/**
* @author Alyanov Artem 23.11.2022
*/

@Component
public class BeforeImplementationRoute extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("direct:beforeImplementationRoute")
                .routeId(Constant.BEFORE_IMPLEMENTATION + "Id")
                .setProperty(Constant.NAMEPROJECT, constant(Constant.NAMEPROJECT))
                .setProperty(Constant.LOG_COMPONENT, constant(Constant.NAMEPROJECT + "REST"))
                .setProperty(Constant.LOG_OPERATION, header(Exchange.HTTP_URI))
                .setProperty(Constant.LOG_STEP, constant("Request"))

                .process(new LogIdGenerator())
                .process(new PrepareRequest())
                .convertBodyTo(String.class)

                .log(LoggingLevel.INFO, Constant.NAMEPROJECT + "REST :: operation - ${exchangeProperty." + Constant.LOG_OPERATION + "}"
                        + " :: TRACE_ID - ${exchangeProperty." + Constant.LOG_ID + "}")

                .wireTap("vm:logging")
                .onPrepare(new WireTapProcessor(
                        simple("${exchangeProperty." + Constant.LOG_COMPONENT + "}"),
                        exchangeProperty(Constant.LOG_OPERATION),
                        Direction.Inbound,
                        exchangeProperty(Constant.LOG_STEP)
                        )
                )
                .copy(true)
                .end()

                .process(exchange -> {
                    Object obj = exchange.getIn().getBody();
                    if (obj != null && obj instanceof Exception) {
                        throw new ApplicationException("Rest route", (Exception) obj);
                    }
                });
    }
}
