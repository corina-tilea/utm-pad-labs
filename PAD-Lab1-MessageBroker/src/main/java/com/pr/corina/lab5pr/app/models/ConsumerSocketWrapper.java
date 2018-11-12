/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.models;

import java.net.Socket;

/**
 *
 * @author corina
 */
public class ConsumerSocketWrapper {
    
    private Socket socket;
    private String topic;
    private String id;
    private String requiredFormat;

    public ConsumerSocketWrapper() {
    }

    public ConsumerSocketWrapper(Socket socket) {
        this.socket = socket;
    }

    
    public ConsumerSocketWrapper(Socket socket, String topic) {
        this.socket = socket;
        this.topic = topic;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequiredFormat() {
        return requiredFormat;
    }

    public void setRequiredFormat(String requiredFormat) {
        this.requiredFormat = requiredFormat;
    }

    @Override
    public String toString() {
        return "ConsumerSocketWrapper{" + "socket=" + socket + ", topic=" + topic + ", id=" + id + ", requiredFormat=" + requiredFormat + '}';
    }
    
}
