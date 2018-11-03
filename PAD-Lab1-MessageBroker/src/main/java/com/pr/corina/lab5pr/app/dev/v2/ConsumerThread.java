package com.pr.corina.lab5pr.app.dev.v2;

/** This is client program which connects to server.
 * It send request for Critical Section when it wants to access it
 * It waits till the time Critical Section is empty
 */

import com.pr.corina.lab5pr.utils.TransactionTypes;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumerThread extends Thread{
    
    private final String consumerType;
    private Long consumerID;
    
    public ConsumerThread(String consumerType){
        super();
        this.consumerType = consumerType;
    }
    
     public void run() {
         try {
             Socket s=new Socket("localhost",7000);
             BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
             
             //Streams
             PrintStream out = new PrintStream(s.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             
            while(true){
                    out.println("CONSUME");
        		
                    String item=in.readLine();

                    System.out.println("Consumer consumed - "  + item);
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
        consumerTh.setConsumerID(Monitor.subscribedConsumers.size()+1L);
        Monitor.subscribedConsumers.put(consumerTh);
        System.out.println("subscribedConsumers="+Monitor.subscribedConsumers.size());
        
        consumerTh.run();
    }
     
     
     
     //Getters & Setters

    public Long getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Long consumerID) {
        this.consumerID = consumerID;
    }
     
     
    
}