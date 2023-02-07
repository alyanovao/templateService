package ru.aao.spring2ws.template.facade.route.initialize;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.common.types.log.Direction;
import ru.aao.spring2ws.template.facade.processor.WireTapProcessor;

/**
* @author Alyanov Artem 23.11.2022
*/

@Component
public class AfterImplementationRoute extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("direct:afterImplementationRoute")
            .routeId(Constant.AFTER_IMPLEMENTATION + "Id")
                .setProperty(Constant.LOG_STEP, constant("Response"))

                .wireTap("vm:logging")
                    .onPrepare(new WireTapProcessor(
                            exchangeProperty(Constant.LOG_COMPONENT),
                            exchangeProperty(Constant.LOG_OPERATION),
                            Direction.Outbound,
                            exchangeProperty(Constant.LOG_STEP)
                        )
                    )
                .end()

                .removeHeader("*");
    }
}
