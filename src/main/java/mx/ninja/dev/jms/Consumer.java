package mx.ninja.dev.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.ninja.dev.config.PropertiesKey;

public class Consumer {
	private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);
	private ConnectionFactory factory = null;
	private Connection conn = null;
	private Session session;
	private Destination destination;
	private MessageConsumer messageConsumer;

	/**
	 * Constructor of the class.
	 * @param config
	 * @throws JMSException
	 */
	public Consumer(Properties config) throws JMSException{
		LOG.debug("Creating JMS consumer.");
		final String userName = config.getProperty(PropertiesKey.USER_NAME);
        final String password = config.getProperty(PropertiesKey.PASSWORD);
        final String brokerURL = config.getProperty(PropertiesKey.BROKER_URL);
        final String topicName = config.getProperty(PropertiesKey.TOPIC_NAME);
        final String consumerId = config.getProperty(PropertiesKey.CONSUMER_ID);
        LOG.info("Destination name: {}", topicName);
        LOG.info("Consumer id: {}", consumerId);
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		conn = factory.createConnection();
		conn.setClientID(consumerId);
		conn.start();
		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createTopic(topicName);
		messageConsumer = session.createDurableSubscriber((Topic)destination, consumerId);
		LOG.info("Consumer created successfully.");
	}
	
	/**
	 * Receive the message from queue.
	 */
	public void receiveMessage(){
		try {
		    Message message = messageConsumer.receive();
			if(message instanceof TextMessage){
				String msg = ((TextMessage)message).getText();
				LOG.info("Message received: {}", msg);
			}
		} catch (JMSException e) {
			LOG.error("An error ocurred when try dequeue message.", e);
		}
		
	}
	
	
}
