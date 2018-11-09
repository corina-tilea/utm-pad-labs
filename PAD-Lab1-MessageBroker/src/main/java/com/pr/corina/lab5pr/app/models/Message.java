/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author corina
 */
public class Message {
    
    private String topic;
    private String msgsContent;
    private String format;
    private List<Long> receiverIdList = new ArrayList();
    public static final int CONSUMER_DATA=1;
    public static final int MESSAGE=2;
    

    public Message(String msgsContent, String format) {
        this.msgsContent = msgsContent;
        this.format = format;
    }
    
    public Message(String topic, String msgsContent, String format) {
        this.topic = topic;
        this.msgsContent = msgsContent;
        this.format = format;
    }

    public String getMsgsContent() {
        return msgsContent;
    }

    public void setMsgsContent(String msgsContent) {
        this.msgsContent = msgsContent;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.msgsContent);
        hash = 71 * hash + Objects.hashCode(this.format);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (!Objects.equals(this.msgsContent, other.msgsContent)) {
            return false;
        }
        if (!Objects.equals(this.format, other.format)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        /*String receivers = "";
        for (int i = 0; i < receiverIdList.size(); i++) {
            receivers += receiverIdList.get(i);
            if(i<receiverIdList.size()-1)
                receivers += ",";
        }*/
        //return format+";"+msgsContent;
        return Message.MESSAGE+";"+topic+";"+msgsContent;
    }

    

}
