/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pad.kafka;

import com.pad.kafka.constants.IKafkaConstants;
import com.pad.kafka.consumer.ConsumerCreator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 *
 * @author corina
 */
public class ConsumerApp {
    
    public static void main(String[] args) {
        runConsumer();
    }
    
    static void runConsumer() {
		Consumer<Long, String> consumer = ConsumerCreator.createConsumer(IKafkaConstants.TOPIC_MESSAGES_BANK1);
                consumer.poll(0);
                consumer.seekToBeginning(consumer.assignment());
		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
					break;
				else
					continue;
			}

			consumerRecords.forEach(record -> {
				System.out.println("Record Key " + record.key());
				System.out.println("Record value " + record.value());
				System.out.println("Record partition " + record.partition());
				System.out.println("Record offset " + record.offset());
			});
			consumer.commitAsync();
		}
		consumer.close();
	}

}
