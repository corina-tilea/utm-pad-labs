/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pr.corina.lab5pr;

import com.google.gson.Gson;
import com.pr.corina.lab5pr.app.models.Transaction;
import com.pr.corina.lab5pr.utils.Serializer;
import com.pr.corina.lab5pr.utils.TransactionTypes;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author andrei
 */
public class SerializationTest {
    
    public static void main(String[] args) {
        
        XStream xstream =  new XStream();
        xstream.processAnnotations(Transaction.class);
        
        Gson gson=new Gson();
        
        Transaction transaction = new Transaction(1,"Me",Long.valueOf("3049385223"),TransactionTypes.XML);
        
        String serializedTransaction=null;
        
        if(transaction.getTransactionFormat()==TransactionTypes.XML){
            serializedTransaction= Serializer.serializeToXML(transaction);
        } else if(transaction.getTransactionFormat()==TransactionTypes.JSON){
            serializedTransaction= Serializer.serializeToJson(transaction);
        }
        
        System.out.println("Serialized Transaction \n"+serializedTransaction);
        
        if(transaction.getTransactionFormat()==TransactionTypes.XML){
            transaction= Serializer.deserializeFromXML(serializedTransaction);
        } else if(transaction.getTransactionFormat()==TransactionTypes.JSON){
            transaction= Serializer.deserializeFromJson(serializedTransaction);
        }
        
        System.out.println("Deserialized Transaction \n"+transaction.toString());
        
        
        
        
        
        
        
        
    }
    
}
