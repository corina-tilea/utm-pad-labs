/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.dev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 *
 * @author corina
 */
public class AppConsumer {

    public static void main(String[] args) throws InterruptedException {
        Socket socket = null;

        PrintStream out = null;
        BufferedReader in = null;
        try {
            socket = new Socket("127.0.0.1", AppServer.PORT_NR);
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            //Input Output Streams
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Ready to consume...");
            while (true) {
                //System.out.println("Want to consume?");
                //String console_inp = sc.readLine();

                /*String msgsRead = in.readLine();
                if(msgsRead != null){
                     out.println("CONSUME");
                     System.out.println("Consumer consumed - " + msgsRead);
                }
                Thread.sleep(new Random().nextInt(3000 - 1000) + 1000);
                */
                //System.out.println("Want to consume?");
        	//String console_inp=sc.readLine();
        	
        	//if(console_inp.equalsIgnoreCase("Yes")){
        		out.println("CONSUME");
        		
        		String item=in.readLine();
            	
        		System.out.println("Consumer consumed - "  + item);
                        
                        Thread.sleep(new Random().nextInt(3000 - 1000) + 1000);
        	//}
                
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis");
        }

    }
}
