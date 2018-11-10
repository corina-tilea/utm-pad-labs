package com.pr.corina.lab5pr.app.dev.v2;

/**
 * This is client program which connects to server. It send request for Critical
 * Section when it wants to access it It waits till the time Critical Section is
 * empty
 */
import com.pr.corina.lab5pr.app.models.Message;
import com.pr.corina.lab5pr.utils.Serializer;
import com.pr.corina.lab5pr.utils.TransactionTypes;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumerThread extends Thread {

    private final String consumerType;
    private Long consumerID;
    private String topic;

    public ConsumerThread(String consumerType) {
        super();
        this.consumerType = consumerType;
    }

    public void run() {
        try {
            Socket s = new Socket("localhost", 7000);
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            //Streams
            PrintStream out = new PrintStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // int id=1;
            boolean msgsWithListenedTopic = false;
            while (true) {
//                    if(id>4){
//                        id=1;
//                        this.setTopic("Audi");
//                    }
                out.println(Message.CONSUMER_DATA + " ; " + this.consumerID + " ; " + this.topic);
                this.setConsumerID(consumerID + 1);
                //id++;
                out.println("CONSUME");

                String line = in.readLine();
                if (line.startsWith("/S/")) {
                    line = line.substring(3);
                     String[] msgsItems = line.split(";");
                     String msgsTopic = msgsItems[0];
                        //String message = msgsItems[1];
                        msgsWithListenedTopic = msgsTopic.equals(topic);
                        //System.out.println("Consumer consumed - " + message);
                }else{
                   if(msgsWithListenedTopic){
                       System.out.println("Consumer consumed - " + line);
                   }
                    

                    
                }

                Thread.sleep(5L);
            }

        } catch (IOException ex) {
            Logger.getLogger(ConsumerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConsumerThread consumerTh = new ConsumerThread(TransactionTypes.JSON);
        consumerTh.setConsumerID(Monitor.subscribedConsumers.size() + 1L);
        consumerTh.setTopic("Volvo");
        Monitor.subscribedConsumers.put(consumerTh);
        System.out.println("subscribedConsumers=" + Monitor.subscribedConsumers.size());

        consumerTh.run();
    }

    //Getters & Setters
    public Long getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Long consumerID) {
        this.consumerID = consumerID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
