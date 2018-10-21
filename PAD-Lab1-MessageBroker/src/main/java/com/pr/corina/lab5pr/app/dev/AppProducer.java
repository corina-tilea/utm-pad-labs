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
public class AppProducer {

    public static void main(String[] args) throws InterruptedException {
        Socket socket = null;

        PrintStream out = null;
        BufferedReader in = null;
        try {
            socket = new Socket("127.0.0.1", AppServer.PORT_NR);
            //Input Output Streams
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int counterMsgs = 0;

            while (true) {
                System.out.println("Produced Messages:");
                counterMsgs++;
                String itemMsgs = "Message nr:" + counterMsgs;
                out.println(itemMsgs);
                Thread.sleep(new Random().nextInt(3000 - 1000) + 1000);
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis");
        } finally {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(AppProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}


