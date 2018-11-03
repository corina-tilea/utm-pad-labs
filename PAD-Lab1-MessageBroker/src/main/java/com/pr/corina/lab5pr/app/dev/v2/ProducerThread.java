package com.pr.corina.lab5pr.app.dev.v2;

/** This is producer class.
 * Class extends Thread. 
 */

import com.pr.corina.lab5pr.app.model.Message;
import com.pr.corina.lab5pr.app.models.Transaction;
import com.pr.corina.lab5pr.utils.AppUtils;
import com.pr.corina.lab5pr.utils.Serializer;
import com.pr.corina.lab5pr.utils.TransactionTypes;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerThread extends Thread{
    
    private final String producerType;
    
    public ProducerThread(String producerType){
        super();
        this.producerType = producerType;
    }
    
    public static void main(String[] args) {
        ProducerThread producerThread = new ProducerThread(TransactionTypes.JSON);
        producerThread.run();
    }
    
    @Override
    public void run(){
        try {
            Socket s = new Socket("localhost",7000);
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            
            //Input Output Streams
            PrintStream out = new PrintStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            int counter = 0;
            
             while(true){
                System.out.println("Produced Messages:");
                counter++;
                String itemMsgs = "Message nr:"+counter;
                
                //Create Message
                Transaction transaction = createTransaction(counter);
                String transactionMsgs = Serializer.serializeToJson(transaction);
                Message msgs = new Message(transactionMsgs, producerType);
                
                out.println(msgs);
                Thread.sleep(3000L);
             }
            
        } catch (IOException ex) {
            Logger.getLogger(ProducerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProducerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Transaction createTransaction(int counter){
         Transaction transaction = new Transaction();
         transaction.setAccountNumber(1000000+counter);
         transaction.setTransactionFormat(producerType);
         transaction.setTransactionId(counter);
         transaction.setOwnerName(AppUtils.generateAlphaString());
         return transaction;
    }
}
    
   

