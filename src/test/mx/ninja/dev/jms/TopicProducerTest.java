/*
 *      File: TopicProducerTest.java
 *    Author: Julio Bolanos <cesar.bolanos@amk-technologies.com>
 *      Date: nov 24, 2017
 * Copyright: AMK Technologies, S.A. de C.V. 2017
 */
package mx.ninja.dev.jms;

import javax.jms.JMSException;

import org.junit.Test;

/**
 *
 * @author Julio Bolanos &lt;cesar.bolanos@amk-technologies.com&gt;
 * @version 1.0.0
 * @since 1.0.0
 */
public class TopicProducerTest {
    
    @Test
    public void test() throws JMSException {
        final String brokerURL = "";
        final String userName = "admin";
        final String password = "admin";
        final String topicName = "JMS_TOPIC_TEST";
        final String message = "HELLO FROM TEST"; 
        
        TopicProducer topicProducer = new TopicProducer(brokerURL, userName, password, topicName);
        
        topicProducer.sendMessage(message);
        
        topicProducer.close();
        
    }
}
