package ru.aao.spring2ws.template.facade.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.aao.spring2ws.template.common.Constant;

/**
* @author Alyanov Artem 23.11.2022
*/

public class PrepareRequest implements Processor {

    @Override
    public void process(Exchange exchange) {
        Object request = exchange.getIn().getBody();
        Object response = null;
        String operationName = "test";
        exchange.setProperty(Constant.REST_REQUEST, request);
        exchange.setProperty(Constant.REST_RESPONSE, response);
        exchange.setProperty(Constant.OPERATION_NAME, operationName);
    }
}
