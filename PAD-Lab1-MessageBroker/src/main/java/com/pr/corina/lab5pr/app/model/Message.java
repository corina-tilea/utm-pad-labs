/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.app.model;

import java.util.Objects;

/**
 *
 * @author corina
 */
public class Message {
    
    private String msgsContent;
    private String format;

    public Message(String msgsContent, String format) {
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
        return format+";"+msgsContent;
    }

}
