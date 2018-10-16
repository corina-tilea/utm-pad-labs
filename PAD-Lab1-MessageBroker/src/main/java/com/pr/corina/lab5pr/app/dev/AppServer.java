/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.dev;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 *
 * @author corina
 */
public class AppServer {

    private static final Logger LOG = Logger.getLogger(AppServer.class.getName());
    public static final Integer PORT_NR = 3456;

    public AppServer() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT_NR);
            LOG.info("ServerSocket creat cu succes.");
            LOG.info("Server in asteptarea conectarii urmatorului client...");
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + PORT_NR + ", " + e);
            System.exit(1);
        }
        while (true) {
            Socket clientSocket = null;
       
            try {
               new AppServerOneThread(serverSocket.accept()).start();
                LOG.info("New user request accepted!");
            } catch (IOException e) {
                System.out.println("Accept failed: " + PORT_NR + ", " + e);
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        AppServer appServer = new AppServer();
    }

}
