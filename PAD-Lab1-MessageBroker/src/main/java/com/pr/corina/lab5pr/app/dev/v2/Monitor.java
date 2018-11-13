package com.pr.corina.lab5pr.app.dev.v2;

// This is client program which connects to server.
// It send request for Critical Section when it wants to access it
// It waits till the time Critical Section is empty
import com.pr.corina.lab5pr.app.models.ConsumerSocketWrapper;
import com.pr.corina.lab5pr.app.models.Transaction;
import com.pr.corina.lab5pr.utils.Serializer;
import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor {

    /* messageQueue - used to store messages from producer */
    public static BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    /* deadLetterMessageQueue - used to store messages haven't been sent due to some exception */
    public static BlockingQueue<String> deadLetterMessageQueue = new LinkedBlockingQueue<>();

    /* consumerSocketsQueue - used to store subscribed Consumers */
    public static BlockingQueue<ConsumerSocketWrapper> consumerSocketsQueue = new LinkedBlockingQueue<>();

    //public static volatile Map<String, String> subscriberTopic = new ConcurrentHashMap<>();    //<subscribers> <topic>
    //public static volatile Map<String, String> subscriberMessages = new ConcurrentHashMap<>(); //<subscribers> <topic;message>
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

        /* ============== Create consumer thread =====================*/
        Thread consumer = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    PrintStream out = null;

                    String item = null;
                    String itemType = null;
                    String itemTopic = null;
                    String itemMessage = null;
                    String itemFormat = null;

                    try {
                        item = consume();
                        /* Serialization */
                        String[] itemValues = item.split(";");
                        itemType = itemValues[0];
                        itemTopic = itemValues[1];
                        itemMessage = itemValues[2];
                        itemFormat = itemValues[3];

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Consumer consumed-" + itemMessage + ", TOPIC=" + itemTopic + ", ITEM_TYPE=" + itemType);

                    for (ConsumerSocketWrapper consumerSocketWrapper : consumerSocketsQueue) {
                        /* Convert Message - to be send */
                        String itemMessageToSend = null;
                        try {
                            itemMessageToSend = convertMessage(itemMessage, itemFormat, consumerSocketWrapper.getRequiredFormat());
                            itemMessageToSend = "/S/" + itemTopic + "\n" + itemMessageToSend;

                            out = new PrintStream(consumerSocketWrapper.getSocket().getOutputStream());
                            BufferedReader in = new BufferedReader(new InputStreamReader(consumerSocketWrapper.getSocket().getInputStream()));
                            /* Show Details */
                            System.out.println("CONSUMER FROM QUEUE=" + consumerSocketWrapper.toString()
                                    + "ITEM TOPIC=" + itemTopic + ", CONS TOPIC=" + consumerSocketWrapper.getTopic() + ", CONS_TYPE=" + consumerSocketWrapper.getRequiredFormat() + ", itemType.equals(consumerSocketWrapper.getRequiredFormat()=" + itemType.equals(consumerSocketWrapper.getRequiredFormat()));

                            /* Send Message */
                            if (itemTopic.equals(consumerSocketWrapper.getTopic())) {
                                if (consumerSocketWrapper.getReceivedMsgsNr() < consumerSocketWrapper.getMsgReceiveLimit()) {
                                    out.println(itemMessageToSend);
                                    consumerSocketWrapper.incrementReceiveMsgsNr();
                                }else{
                                    throw new Exception("Limit of Messages Exceeded");
                                }
                            }
                        } catch (Exception ex) {
                            deadLetterMessageQueue.add(consumerSocketWrapper.getId() + ";" + itemMessageToSend);
                            System.out.println("[EXCEPTION]:"+ex.getMessage());
                            System.out.println("==== deadLetterMessageQueue:"+deadLetterMessageQueue.toString());
                        }
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

        /* Accept consumer */
        while (true) {
            Socket ss2 = s.accept();
            System.out.println("================================ Consumer Accepted =====================");
            BufferedReader in = new BufferedReader(new InputStreamReader(ss2.getInputStream()));
            String call = in.readLine();
            String[] callFragments = call.split(";");
            ConsumerSocketWrapper consumerSocketWrapp = new ConsumerSocketWrapper(ss2);
            consumerSocketWrapp.setId(callFragments[1]);
            consumerSocketWrapp.setTopic(callFragments[2]);
            consumerSocketWrapp.setRequiredFormat(callFragments[3]);
            consumerSocketsQueue.add(consumerSocketWrapp);
        }

    }

    public static String convertMessage(String msgs, String msgFormat, String requiredConsumerFormat) {
        String convertedMsgs = msgs;
        /* Check format - and serialize */
        if (!msgFormat.equals(requiredConsumerFormat)) {
            Transaction readTransaction = Serializer.deserializeFromJson(msgs);
            convertedMsgs = Serializer.serializeToXML(readTransaction);
        }
        return convertedMsgs;
    }

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

}
