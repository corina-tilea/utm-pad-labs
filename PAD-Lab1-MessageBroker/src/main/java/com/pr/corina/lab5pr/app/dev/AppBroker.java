/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.dev;

import java.io.IOException;
import java.net.ServerSocket;
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

    public static BlockingQueue<String> item_q = new LinkedBlockingQueue<String>();
    
    public AppBroker() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT_NR);
            LOG.info("AppBroker Socket -  creat cu succes.");
            new AppBrokerThread(serverSocket.accept(), AppConstants.CLIENT_TYPE_PRODUCER).start();

        } catch (IOException e) {
            System.out.println("Could not listen on port: " + PORT_NR + ", " + e);
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        AppBroker appBroker = new AppBroker();
    }

}
