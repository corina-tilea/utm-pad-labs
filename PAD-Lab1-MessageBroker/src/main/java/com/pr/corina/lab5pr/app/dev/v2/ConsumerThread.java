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

                out.println(Message.CONSUMER_DATA + ";" + this.consumerID + ";" + this.topic+";"+this.consumerType);
                this.setConsumerID(consumerID + 1);
                //id++;
                out.println("CONSUME");

                String line = in.readLine();
                System.out.println("Consumer consumed - " + line);
                //if(line != null)
                /*if (line.startsWith("/S/")) {
                    line = line.substring(3);
                     //String[] msgsItems = line.split(";");
                     String msgsTopic = line;
                        //String message = msgsItems[1];
                        msgsWithListenedTopic = msgsTopic.equals(topic);
                        //System.out.println("Consumer consumed - " + message);
                }else{
                   if(msgsWithListenedTopic){
                       System.out.println("Consumer consumed - " + line);
                   }
                    

                    
                }*/

                Thread.sleep(5L);
            }

        } catch (IOException ex) {
            Logger.getLogger(ConsumerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConsumerThread consumerTh = new ConsumerThread(TransactionTypes.XML);
        consumerTh.setConsumerID(1L);
        consumerTh.setTopic("BMW");

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
