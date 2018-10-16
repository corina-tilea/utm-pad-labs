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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corina
 */
public class AppConsumer {
    public static void main(String[] args) throws InterruptedException {
        Socket kkSocket = null;
        //PrintWriter pw = null;
        //BufferedReader br = null;

        PrintStream out = null;
        BufferedReader in = null;
        try {
            kkSocket = new Socket("127.0.0.1", AppServer.PORT_NR);
            //pw = new PrintWriter(kkSocket.getOutputStream());
            //br = new BufferedReader(new InputStreamReader(System.in));
             //Input Output Streams
             out = new PrintStream(kkSocket.getOutputStream());
             in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            //new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis");
        }
        
        
        
        String inputLine;

        if (kkSocket != null && out != null && in != null) {
            try {
                while ((inputLine = in.readLine()) != null) {
                    //outputLine = serverProtocol.processInput(inputLine);
                    //pw.println(inputLine);
                    String msgs  = "MSG_FROM_Broker : "+ inputLine;
                    
                    out.flush();
                    if (inputLine.equals("Bye"))
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(AppConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}