package mx.ninja.dev.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.ninja.dev.config.PropertiesKey;

public class Producer {
    private static final Logger LOG = LoggerFactory.getLogger(Producer.class);
    private ConnectionFactory factory = null;
    private Connection conn = null;
    private Session session;
    private Destination destination;
    private MessageProducer messageProducer;

 

    /**
     * Constructor of class.
     * 
     * @param config
     * @throws JMSException
     */
    public Producer(Properties config) throws JMSException {
        LOG.debug("Creating JMS producer.");
        final String userName = config.getProperty(PropertiesKey.USER_NAME);
        final String password = config.getProperty(PropertiesKey.PASSWORD);
        final String brokerURL = config.getProperty(PropertiesKey.BROKER_URL);
        final String topicName = config.getProperty(PropertiesKey.TOPIC_NAME);
        factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
        conn = factory.createConnection();
        conn.setClientID("PRODUCER_CLOUD");
        conn.start();
        session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);        
        destination = session.createTopic(topicName);        
        messageProducer = session.createProducer(destination);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        LOG.info("Producer created successfully.");
    }

   

    /**
     * Send a string message.
     * 
     * @param message
     */
    public void sendMessage(String message) {
        try {
            TextMessage msg = session.createTextMessage(message);
            messageProducer.send(msg);
        } catch (JMSException e) {
            LOG.error("An error ocurred when try send message to queue.");
        }
    }

    public void closeProducer() throws JMSException {
        session.close();
        conn.close();
    }
}
