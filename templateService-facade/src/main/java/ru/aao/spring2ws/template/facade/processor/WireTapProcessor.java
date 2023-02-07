package ru.aao.spring2ws.template.facade.processor;

import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.ValueBuilder;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.common.types.log.Direction;

/**
* @author Alyanov Artem 22.11.2022
*/

@AllArgsConstructor
public class WireTapProcessor implements Processor {

    private ValueBuilder component;
    private ValueBuilder operation;
    private Direction direction;
    private ValueBuilder step;

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader(Constant.LOG_COMPONENT, component.convertToString());
        exchange.getIn().setHeader(Constant.LOG_OPERATION, operation.convertToString());
        exchange.getIn().setHeader(Constant.LOG_DIRECTION, direction);
        exchange.getIn().setHeader(Constant.LOG_STEP, step.convertToString());
    }
}
