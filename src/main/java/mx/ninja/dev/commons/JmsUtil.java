/**
 * 
 */
package mx.ninja.dev.commons;

import java.util.Properties;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.ninja.dev.config.DestinationType;
import mx.ninja.dev.config.PropertiesKey;

/**
 *
 * @author Julio Cesar Bolaños Palacios
 *         &lt;cesar.bolanos@amk-technologies.com&gt;
 * @version 1.0.0
 * @since 1.0.0
 */
public class JmsUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JmsUtil.class);
    
    /**
     * Create destination.
     * @param config
     * @param session
     * @return
     * @throws JMSException
     */
    public static Destination createDestination(Properties config, Session session) throws JMSException {
        Destination destination = null;
        String destinationType = config.getProperty(PropertiesKey.DESTINATION_TYPE, DestinationType.QUEUE.name());
        DestinationType destType = DestinationType.valueOf(destinationType);
        LOG.info("Create destination of type: {}", destType);
        if (destType.equals(DestinationType.QUEUE)) {
            final String queueName = config.getProperty(PropertiesKey.QUEUE_NAME);
            LOG.info("Destination name is: {}", queueName);
            destination = session.createQueue(queueName);
        } else if (destType.equals(DestinationType.TOPIC)) {
            final String topicName = config.getProperty(PropertiesKey.TOPIC_NAME);
            LOG.info("Destination name is: {}", topicName);
            destination = session.createTopic(topicName);
        }
        return destination;
    }
}
