/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.dev;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author corina
 */
public class AppBrokerThread extends Thread {
    Socket socket = null;
    private String threadId;

    public AppBrokerThread(Socket socket, String clientType) {
        super("AppBrokerThread");
        this.socket = socket;
        threadId = RandomStringUtils.randomAlphanumeric(8);
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                                  new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(
                              new BufferedOutputStream(socket.getOutputStream(), 1024), false);
            ServerProtocol serverProtocol = new ServerProtocol();
            String inputLine, outputLine;

            //outputLine = serverProtocol.processInput(null);
            //pw.println(outputLine);
           // pw.flush();

            while ((inputLine = br.readLine()) != null) {
                //outputLine = serverProtocol.processInput(inputLine);
                //pw.println(inputLine);
                String msgs  = "MSG_FROM_CLIENT "+"["+threadId+"] : "+ inputLine;
                AppBroker.item_q.put(msgs);
                System.out.println(msgs+ " in queue="+AppBroker.item_q.size());
                
                pw.flush();
                if (inputLine.equals("Bye"))
                    break;
            }
            pw.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(AppBrokerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

