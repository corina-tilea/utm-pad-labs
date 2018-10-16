package com.pr.corina.lab5pr.app.dev.v2;

/****** DHIRAJ D GANDHI ********/
// This is producer class.

import java.io.*;
import java.net.*;

public class Producer
{
    public static void main(String args[])throws IOException, InterruptedException
    {
        Socket s=new Socket("localhost",7000);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        
        //Input Output Streams
        PrintStream out = new PrintStream(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
       int counter = 0;
        while(true){
        	//System.out.println("Want to produce?");
        	//String console_inp=sc.readLine();
                System.out.println("Produced Messages:");
                counter++;
                String itemMsgs = "Message nr:"+counter;
                out.println(itemMsgs);
                Thread.sleep(1000L);
        	
        	/*if(console_inp.equalsIgnoreCase("Yes")){
        		String item=sc.readLine();
        		
        		out.println(item);
        		
        		in.readLine();
        		System.out.println("Producer produced - "  + item);
        		
        	}*/
        	 
        	
        }
        
    }
}


