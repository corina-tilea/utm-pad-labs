package com.pr.corina.lab5pr.app.dev.v2;

/**
 * **** DHIRAJ D GANDHI *******
 */
// This is client program which connects to server.
// It send request for Critical Section when it wants to access it
// It waits till the time Critical Section is empty
import com.pr.corina.lab5pr.app.models.Message;
import com.pr.corina.lab5pr.app.models.Transaction;
import com.pr.corina.lab5pr.utils.Serializer;
import com.pr.corina.lab5pr.utils.TransactionTypes;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor {

    public static BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Thread> subscribedConsumers = new LinkedBlockingQueue<>();
    public static volatile Map<String, String> subscriberTopic = new ConcurrentHashMap<>();    //<subscribers> <topic>
    public static volatile Map<String, String> subscriberMessages = new ConcurrentHashMap<>(); //<subscribers> <topic;message>
    static int capacity = 100;

    public static void main(String args[]) throws IOException, InterruptedException {
        ServerSocket s = new ServerSocket(7000);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

        /* Accept producer */
        Socket ss1 = s.accept();

        /* Create producer thread */
        Thread producer = new Thread(new Runnable() {
            PrintStream out = new PrintStream(ss1.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(ss1.getInputStream()));

            @Override
            public void run() {
                while (true) {

                    try {
                        Object[] topicsArray = subscriberTopic.keySet().toArray();
                        if (topicsArray.length > 0) {
                            String firstTopic = subscriberTopic.keySet().stream().findFirst().get();
                            out.println(firstTopic);
                        }
                        /* Versiunea cu MAP */
 /*String call = in.readLine();
                        String[] responseFragments = call.split(";");
                        processCall(responseFragments);*/

 /* Versiunea cu QUEUE */
                        String item = null;

                        item = in.readLine();
                        try {
                            if (item != null) {
                                produce(item);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    out.println("PRODUCE");
                }
            }
        });

        producer.start();

        /* Accept consumer */
        Socket ss2 = s.accept();
        System.out.println("================================ Consumer Accepted =====================");
        /* Create consumer thread */
        Thread consumer = new Thread(new Runnable() {
            
            PrintStream out = new PrintStream(ss2.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(ss2.getInputStream()));

            @Override
            public void run() {
                int counterLoop = 0;

                while (true) {

                    if (counterLoop == 0) {
                        try {
                            String call = in.readLine();
                            String[] callFragments = call.split(";");
                            processCall(callFragments);
                            counterLoop++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        /* Versiunea cu QUEUE */
                        String item = null;
                        String itemType = null;
                        String itemTopic = null;
                        String itemMessage = null;

                        try {
                            item = consume();
                            /* Serialization */
                            String[] itemValues = item.split(";");
                            itemType = itemValues[0];
                            itemTopic = itemValues[1];
                            itemMessage = itemValues[2];
                            if (!itemType.equals(TransactionTypes.XML)) {
                                Transaction readTransaction = Serializer.deserializeFromJson(itemMessage);
                                itemMessage = Serializer.serializeToXML(readTransaction);
                            }
                            /* Add Topic */
                            itemMessage = "/S/" + itemTopic + "\n" + itemMessage;

                            /*String[] messagesArray = (String[]) subscriberMessages.keySet().toArray();
                            if (messagesArray.length > 0) {
                                String firstTopic = subscriberTopic.get(messagesArray[0]);
                                out.println(firstTopic);
                            }*/
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //Set receivers
                        printSubscriberListSize();
                        //itemMessage = computeReceiversParam() + itemMessage;
                        System.out.println("Consumer consumed-" + itemMessage);
                        out.println(itemMessage);

                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
    

        consumer.start();
         
      

    }

    /*public static String computeReceiversParam(String messageTopic) {
        String receivers = "";
        for (ConsumerThread consumerTh : subscribedConsumers) {
            receivers += consumerTh.getId() + ",";
        }
        return receivers;
    }*/
    public static void print_q() {
        System.out.println("---Queue elements----");

        for (String s : messageQueue) {
            System.out.print(s + " | ");
        }
        System.out.println("\n-------------");
    }

    // Function called by producer thread
    public static void produce(String value) throws InterruptedException {

        /* producer thread waits while list is full */
        while (messageQueue.size() == capacity) {
            Thread.sleep(3000);
        }

        System.out.println("Producer produced-" + value);

        /*to insert the jobs in the list*/
        //synchronized(item_q){
        messageQueue.put(value);
        print_q();
        //System.out.println("Lock with producer");
        //Thread.sleep(3000);
        //}

        // notifies the consumer thread that
        // now it can start consuming
        // notify();
        // makes the working of program easier
        // to  understand
        //Thread.sleep(3000);
    }

    // Function called by consumer thread
    public static String consume() throws InterruptedException {

        // consumer thread waits while list
        // is empty
        while (messageQueue.size() == 0) {
            Thread.sleep(3000);
        };

        //to retrive the first job in the list
        String val = null;
        // synchronized(item_q){
        val = messageQueue.poll();
        //System.out.println("Lock with consumer");
        //Thread.sleep(3000);
        //}
        //System.out.println("Consumer consumed-" + val);
        print_q();
        // and sleep
        //Thread.sleep(3000);

        return val;

    }

    public static void printSubscriberListSize() {
        System.out.println("SIZE SUBSCR = " + subscribedConsumers.size());
    }

    public static void processCall(String[] callFragments) {
        int nrOfFragments = callFragments.length;
        System.out.println("CALL SIZE = " + callFragments.length);
        if (nrOfFragments > 1) {
            int callType = Integer.valueOf(callFragments[0].trim());
            if (callType == Message.CONSUMER_DATA) {
                processConsumerMessage(callFragments);
            } else if (callType == Message.MESSAGE) {
                processProducerMessage(callFragments);
            }
        }

        System.out.println("subscriberTopic SIZE = " + subscriberTopic.size());
        System.out.println("*** " + subscriberTopic.toString());
    }

    public static void processConsumerMessage(String[] callFragments) {
        System.out.println("### Monitor recieved consumer's message ###");
        String customerId = callFragments[1].trim();
        String topic = callFragments[2].trim();
        System.out.println("# CONSUMER_ID = " + customerId + " ... TOPIC = " + topic);
        addCustomerTopic(Long.valueOf(customerId), topic);
    }

    public static void processProducerMessage(String[] callFragments) {
        String topic = callFragments[1];
        String message = callFragments[2];

        String key = "";
        for (Map.Entry<String, String> entry : subscriberTopic.entrySet()) {
            if (entry.getValue().equals(topic)) {
                key = entry.getKey();
                break;
                //System.out.println("#*#*# KEY = "+key+" ... VALUE = "+value);
            }
        }
        //System.out.println("### Monitor recieved producer's message ###");
        subscriberMessages.put(key, topic + ";" + message);
        printSubscriberMessagesMap();
        //System.out.println("***###"+subscriberMessages.toString());

    }

    public static void printSubscriberMessagesMap() {
        System.out.println("---Subscriber Messages elements----");

        for (Entry<String, String> subscriberMsgs : subscriberMessages.entrySet()) {
            System.out.print("[" + subscriberMsgs.getValue() + "]" + subscriberMsgs.getValue() + " | ");
        }
        System.out.println("\n-------------");
    }

    public static synchronized void addCustomerTopic(Long id, String topic) {
        if (subscriberTopic.size() == 0) {
            insertIntoMap(id.toString(), topic);
        } else {
            modifyMap(id, topic);
        }
    }

    private static synchronized void modifyMap(Long id, String topic) {
        if (subscriberTopic.containsValue(topic)) {
            String key = "";
            String value = "";
            for (Map.Entry<String, String> entry : getEntrySet()) {
                if (entry.getValue().equals(topic)) {
                    key = entry.getKey();
                    value = entry.getValue();
                    break;
                    //System.out.println("#*#*# KEY = "+key+" ... VALUE = "+value);
                }
            }
            removeFromMap(key);
            key = key + ";" + id;
            insertIntoMap(key, value);
        } else {
            insertIntoMap(id.toString(), topic);
        }

    }

    public static synchronized void insertIntoMap(String idList, String topic) {
        subscriberTopic.put(idList, topic);
    }

    public static synchronized void removeFromMap(String idList) {
        subscriberTopic.remove(idList);
    }

    public static synchronized Set<Entry<String, String>> getEntrySet() {
        return subscriberTopic.entrySet();
    }

}
