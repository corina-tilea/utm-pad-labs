package com.pr.corina.lab5pr.app.dev.v2;

/**
 * **** DHIRAJ D GANDHI *******
 */
// This is client program which connects to server.
// It send request for Critical Section when it wants to access it
// It waits till the time Critical Section is empty
import com.pr.corina.lab5pr.app.models.Transaction;
import com.pr.corina.lab5pr.utils.Serializer;
import com.pr.corina.lab5pr.utils.TransactionTypes;
import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor {

    public static BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
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
                    String item = null;
                    try {
                        item = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(item!=null)
                            produce(item);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    out.println("PRODUCE");
                }
            }
        });

        producer.start();

        /* Accept consumer */
        Socket ss2 = s.accept();

        /* Create consumer thread */
        Thread consumer = new Thread(new Runnable() {
            PrintStream out = new PrintStream(ss2.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(ss2.getInputStream()));

            @Override
            public void run() {
                while (true) {
                    try {
                        in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String item = null;
                    String itemType = null;
                    String itemMessage=null;
                    
                    try {
                        item = consume();
                        String[] itemValues = item.split(";");
                        itemType = itemValues[0];
                        itemMessage = itemValues[1];
                        if(!itemType.equals(TransactionTypes.XML)){
                            Transaction readTransaction = Serializer.deserializeFromJson(itemMessage);
                            itemMessage = Serializer.serializeToXML(readTransaction);
                        }
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Consumer consumed-" + itemMessage);
                    out.println(itemMessage);
                    
                    
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

        System.out.println("Producer produced-"+ value);

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
