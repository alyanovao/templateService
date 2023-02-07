package ru.aao.spring2ws.template.facade.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.ResponseEntity;
import ru.aao.spring2ws.template.common.exception.error.Error;
import ru.aao.spring2ws.template.common.exception.error.ErrorDetail;
import ru.aao.spring2ws.template.common.exception.ExceptionUtils;

public class RestFaultResponseBuilder implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        int status = 400;

        Exception exception = ExceptionUtils.exceptionExtractor(exchange);
        Error error;
        if (exception != null) {
            error = new Error();
            error.setCode(status);
            error.setMessage(exception.getMessage());
        } else {
            error = new Error(ErrorDetail.Inner_error, "");
        }
        ResponseEntity response = ResponseEntity.status(status).body(error);
        exchange.getOut().setBody(response);
    }
}
