/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pad.kafka.producer;

import com.pad.kafka.constants.IKafkaConstants;
import com.pad.kafka.pojo.Payment;
import java.util.Date;
import java.util.logging.Logger;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author corina
 */
public class MessageProducer implements Runnable {

    private static final Logger LOG = Logger.getLogger(MessageProducer.class.getName());
    
    private Producer producer;

    public MessageProducer() {
        LOG.info("Message producer Created!");
        producer = ProducerCreator.createProducer();
    }
    
    

    
     @Override
    public void run() {
        LOG.info("Message producer run method entered!");
        int paymentMsgsCount = 0;
        try {
            while (true){
                Payment payment = new Payment();
                payment.setId((long)paymentMsgsCount+1);
                payment.setBank(RandomStringUtils.randomAlphanumeric(10));
                payment.setDate(new Date());
                //Set data in Payment
                //Review review = reviewsQueue.poll();
                
                LOG.info("Sending payment msgs  " + paymentMsgsCount + ": " + payment.getBank());
                producer.send(new ProducerRecord<>(IKafkaConstants.TOPIC_MESSAGES_BANK1, payment.getBank()));
                 LOG.info("Sent! payment msgs  " + paymentMsgsCount + ": " + payment);    
                // sleeping to slow down the pace a bit
                Thread.sleep(2000);
                   
                 paymentMsgsCount += 1;    
            }
        } catch (InterruptedException e) {
            LOG.warning(" Producer interrupted");
        } finally {
            close();
        }
    }

    public void close() {
        LOG.info("Closing Producer");
        producer.close();
    }
    
}
