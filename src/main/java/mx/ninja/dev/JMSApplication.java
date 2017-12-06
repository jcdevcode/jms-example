package mx.ninja.dev;

import java.util.Properties;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.ninja.dev.cl.Command;
import mx.ninja.dev.config.DefaultValues;
import mx.ninja.dev.config.PropertiesKey;
import mx.ninja.dev.jms.Consumer;
import mx.ninja.dev.jms.Producer;

public class JMSApplication {

	private static final Logger LOG = LoggerFactory.getLogger(JMSApplication.class);

	public static void start(Properties config, Command command) {
		switch (command) {
		case CONSUMER:
			createConsumer(config);
			break;
		case PRODUCER:
			createProducer(config);
			break;
		default:
			break;
		}
	}

	private static void createProducer(Properties config) {
		Producer producer = null;
		try {
			 producer = new Producer(config);
			int numMessage = Integer
					.valueOf(config.getProperty(PropertiesKey.NUMBER_MESSAGES, DefaultValues.NUMBER_MESSAGES));
			String message = config.getProperty(PropertiesKey.MESSAGE, DefaultValues.MESSAGE);
			for (int i = 0; i < numMessage; i++) {
				LOG.info("Sendig message {} of {}", i + 1, numMessage);
				producer.sendMessage(message);
			}			
		} catch (JMSException e) {
			LOG.error("An error ocurred when try create the producer.", e);
		}
		
		try {
			producer.closeProducer();
		} catch (JMSException e) {
			LOG.error("An error ocurred when try close the producer.", e);
		}
	}
	
	
	

	private static void createConsumer(Properties config) {
		try {
			Consumer consumer = new Consumer(config);
			while(true){
				consumer.receiveMessage();
			}
		} catch (JMSException e) {
			LOG.error("An error ocurred when try create the consumer.", e);
		}
	}
}
