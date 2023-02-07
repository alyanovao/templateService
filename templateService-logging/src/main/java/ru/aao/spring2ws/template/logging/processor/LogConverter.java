package ru.aao.spring2ws.template.logging.processor;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import ru.aao.spring2ws.template.common.Constant;
import ru.aao.spring2ws.template.common.types.log.Direction;
import ru.aao.spring2ws.template.common.types.log.LogRecord;
import ru.aao.spring2ws.template.common.util.MessageUtils;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class LogConverter implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Gson gson = new Gson();

		LogRecord record = new LogRecord();
		record.setSystem(exchange.getIn().getHeader(Constant.NAMEPROJECT, String.class));
		record.setTraceId(exchange.getProperty(Constant.LOG_ID, String.class));
		record.setSpanId(exchange.getProperty(Constant.CORRELATION_ID, String.class));
		record.setTimestamp(DateTime.now());

		record.setComponent(exchange.getIn().getHeader(Constant.LOG_COMPONENT, String.class));
		record.setOperation(exchange.getIn().getHeader(Constant.LOG_OPERATION, String.class));
		record.setDirection(exchange.getIn().getHeader(Constant.LOG_DIRECTION, Direction.class));
		record.setStep(exchange.getIn().getHeader(Constant.LOG_STEP, String.class));

		try {
			record.setHost(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e1) {
			record.setHost("unknown");
		}

		Object redelivery = exchange.getIn().getHeader(Constant.LOG_REDELIVERY) == null ? null
				: exchange.getIn().getHeader(Constant.LOG_REDELIVERY, String.class);
		Object body = exchange.getMessage().getBody() == null ? exchange.getIn().getBody() : exchange.getMessage().getBody();

		Object obj = redelivery == null ? body : redelivery;
		if (obj instanceof InputStream) {
			record.setData(MessageUtils.getStringFromInputStream((InputStream) obj));
		} else if (obj instanceof String) {
			record.setData(obj);
		} else {
			record.setData(gson.toJson(obj));
		}

		exchange.getMessage().setBody(gson.toJson(record));
		exchange.setProperty("record", record);
	}

}
