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

/**
 *
 * @author corina
 */
public class AppServerOneThread extends Thread {
    Socket socket = null;

    public AppServerOneThread(Socket socket) {
        super("AppServerOneThread");
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                                  new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(
                              new BufferedOutputStream(socket.getOutputStream(), 1024), false);
            ServerProtocol serverProtocol = new ServerProtocol();
            String inputLine, outputLine;

            outputLine = serverProtocol.processInput(null);
            pw.println(outputLine);
            pw.flush();

            while ((inputLine = br.readLine()) != null) {
                outputLine = serverProtocol.processInput(inputLine);
                pw.println(outputLine);
                System.out.println("MSG_FROM_CLIENT: " + outputLine);
                pw.flush();
                if (outputLine.equals("Bye"))
                    break;
            }
            pw.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
