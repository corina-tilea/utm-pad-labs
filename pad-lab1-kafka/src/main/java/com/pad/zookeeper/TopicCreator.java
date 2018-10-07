/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pad.zookeeper;

import com.pad.kafka.constants.IKafkaConstants;
import java.util.Properties;
import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 *
 * @author corina
 */
public class TopicCreator {

    public static void createTopic(String topicName) {
        ZkClient zkClient = null;
        ZkUtils zkUtils = null;

        try {
            String zookeeperHosts = "localhost:2181";
            int sessionTimeOutInMs = 15 * 1000;
            int connectionTimeOutInMs = 10 * 1000;

            zkClient = new ZkClient(zookeeperHosts, sessionTimeOutInMs, connectionTimeOutInMs, ZKStringSerializer$.MODULE$);
            zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperHosts), false);

            int noOfPartition = 1;
            int noOfReplication = 1;
            Properties topicConfiguration = new Properties();

            AdminUtils.createTopic(zkUtils, topicName, noOfPartition, noOfReplication, topicConfiguration, RackAwareMode.Enforced$.MODULE$);
            zkClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TopicCreator.createTopic(IKafkaConstants.TOPIC_MESSAGES_BANK1);
    }

}
