package ru.aao.spring2ws.template.logging.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.logging.processor.LogConverter;

@Component
public class LoggingRoute extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("vm:logging?concurrentConsumers={{logging.cc:3}}&waitForTaskToComplete=Never")
        	.routeId("loggingId")
        	.setExchangePattern(ExchangePattern.InOnly)

        	.process(new LogConverter())
        	
        	.log(LoggingLevel.INFO, Constant.NAMEPROJECT,"${body}")
        .end();
        
    }
}

