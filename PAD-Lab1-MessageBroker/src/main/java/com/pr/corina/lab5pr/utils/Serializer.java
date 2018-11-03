/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr.utils;

import com.google.gson.Gson;
import com.pr.corina.lab5pr.app.models.Transaction;
import com.thoughtworks.xstream.XStream;

/**
 *
 * @author andrei
 */
public class Serializer {
    private static XStream xstream;
    private static Gson gson;

    static{
        xstream=new XStream();
        xstream.processAnnotations(Transaction.class);
        gson=new Gson();
    }
        
    
    
    
    public static String serializeToXML(Transaction transaction){
        return xstream.toXML(transaction);
    }
    
    
    public static Transaction deserializeFromXML(String serializedTransaction){
        return (Transaction) xstream.fromXML(serializedTransaction);
    }
    
    public static String serializeToJson(Transaction transaction){
        return gson.toJson(transaction);
    }
    
    public static Transaction deserializeFromJson(String serializedTransaction){
        return (Transaction) gson.fromJson(serializedTransaction, Transaction.class);
    }
    
}
