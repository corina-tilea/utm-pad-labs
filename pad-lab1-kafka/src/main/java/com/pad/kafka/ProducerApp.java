/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pad.kafka;

import com.pad.kafka.constants.IKafkaConstants;
import com.pad.kafka.producer.MessageProducer;
import com.pad.kafka.producer.ProducerCreator;
import static com.yammer.metrics.Metrics.shutdown;
import static java.lang.Math.log;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 *
 * @author corina
 */
public class ProducerApp {

    private static final Logger LOG = Logger.getLogger(ProducerApp.class.getName());
    
    
    private ExecutorService executor;
    private MessageProducer messageProducer;

    public ProducerApp() {
        executor = Executors.newFixedThreadPool(2);
        messageProducer = new MessageProducer();
    }
    
    
    public static void main(String[] args) {
        ProducerApp producerApp = new ProducerApp();
        producerApp.start();
        //runProducer();
    }
    
    private void start() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!executor.isShutdown()){
                LOG.info("Shutdown requested");
                //shutdown();
            }
        }));

        LOG.info("Application started!");
        executor.execute(messageProducer);
        LOG.info("Stuff submit");
//        try {
//            LOG.info("Latch await");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            shutdown();
//            log.info("Application closed succesfully");
//        }
    }
    
    static void runProducer() {
		Producer<Long, String> producer = ProducerCreator.createProducer();
                while(true){
                    try {
                        Scanner scanner = new Scanner(System.in);
                        String message = scanner.nextLine();
                        final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_MESSAGES_BANK1,message);
                        
                        RecordMetadata metadata = producer.send(record).get();
                        /*System.out.println("Record sent to partition " + metadata.partition()
                                + " with offset " + metadata.offset());*/
                        
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProducerApp.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(ProducerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
		
	}
    
}
