/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.dev;

import static com.pr.corina.lab5pr.app.dev.v2.Monitor.consume;
import static com.pr.corina.lab5pr.app.dev.v2.Monitor.item_q;
import static com.pr.corina.lab5pr.app.dev.v2.Monitor.print_q;
import static com.pr.corina.lab5pr.app.dev.v2.Monitor.produce;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 *
 * @author corina
 */
public class AppBroker {

    private static final Logger LOG = Logger.getLogger(AppServer.class.getName());
    public static final Integer PORT_NR = 3456;
    static int capacity = 100;

    public static BlockingQueue<String> msgsQueue = new LinkedBlockingQueue<String>();

    public static void printQueue() {
        System.out.println("---Queue elements----");

        for (String s : msgsQueue) {
            System.out.print(s.toString() + " | ");
        }
        System.out.println("\n-------------");
    }
    
    public static void main(String args[]) throws IOException, InterruptedException {
        ServerSocket s = new ServerSocket(PORT_NR);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

        //Accept producer
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
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        if(item!=null)
                            produce(item);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    out.println("PRODUCE");
                }
            }
        });

        producer.start();

        //Accept consumer
        Socket ss2 = s.accept();

        // Create consumer thread
        Thread consumer = new Thread(new Runnable() {
            PrintStream out = new PrintStream(ss2.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(ss2.getInputStream()));

            @Override
            public void run() {
                while (true) {
                    try {
                        in.readLine();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String item = null;
                    try {
                        item = consume();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    out.println(item);
                }
            }
        });

        consumer.start();

    }
    
     // Function called by producer thread
    public static void produce(String value) throws InterruptedException {

        // producer thread waits while list
        // is full
        while (msgsQueue.size() == capacity) {
            Thread.sleep(3000);
        }

        System.out.println("Producer produced-"
                + value);

        // to insert the jobs in the list
        //synchronized(item_q){
        msgsQueue.put(value);
        printQueue();
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
        while (msgsQueue.size() == 0) {
            Thread.sleep(3000);
        };

        //to retrive the first job in the list
        String val = null;
        // synchronized(item_q){
        val = msgsQueue.poll();
        //System.out.println("Lock with consumer");
        //Thread.sleep(3000);
        //}
        System.out.println("Consumer consumed-"
                + val);
        printQueue();
        // and sleep
        //Thread.sleep(3000);

        return val;

    }
    

}
