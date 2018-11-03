/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pr.corina.lab5pr.utils;

import com.pr.corina.lab5pr.utils.Levenshtein;
import com.pr.corina.lab5pr.utils.ChatConstants;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author corina
 */
public class ServerProtocol {
    private static final int INITIALIZED= 0;
    private static final int WAITING_FOR_MESSAGE= 1;
    private static final int NUMJOKES = 5;
    private int state = INITIALIZED;
    
    public String processInput(String theInput) {
        String theOutput = null;

        if (state == INITIALIZED) {
            
            theOutput = ChatConstants.WELCOME+ChatConstants.PROTOCOL_NEW_LINE+ChatConstants.MSG_HELP;
            state = WAITING_FOR_MESSAGE;
        } else if (state == WAITING_FOR_MESSAGE) {
            if (theInput.equalsIgnoreCase(ChatConstants.CMD_HELP)) {
                theOutput = ChatConstants.MSG_SUPPORTED_COMMANDS+ChatConstants.PROTOCOL_NEW_LINE+
                            ChatConstants.MSG_HELLO_PARAM+ChatConstants.PROTOCOL_NEW_LINE+
                            ChatConstants.MSG_TUTORIAL_TIME+ChatConstants.PROTOCOL_NEW_LINE+
                            ChatConstants.MSG_TUTORIAL_PASSWORD+ChatConstants.PROTOCOL_NEW_LINE+
                            ChatConstants.MSG_TUTORIAL_JOKE+ChatConstants.PROTOCOL_NEW_LINE+
                            ChatConstants.MSG_HELP+ChatConstants.PROTOCOL_NEW_LINE;
            } else if(theInput.startsWith(ChatConstants.CMD_HELLO)){
                String messageParam=theInput.substring(7);
                theOutput=ChatConstants.MSG_HELLO+messageParam+ChatConstants.PROTOCOL_NEW_LINE+
                          ChatConstants.MSG_PARAMETER;
            }else if(theInput.startsWith(ChatConstants.CMD_TIME)){
                Calendar calendar=Calendar.getInstance();
                Date date=calendar.getTime();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String dateStr=dateFormat.format(date);
                theOutput=ChatConstants.MSG_CURRENT_TIME+dateStr;
            }else if(theInput.startsWith(ChatConstants.CMD_JOKE)){
                //JokesManager jokesManager= null;//new JokesManager();
               // theOutput=jokesManager.getRandomJoke();
            
            }else if(theInput.startsWith(ChatConstants.CMD_PASSWORD)){
                //theOutput=AppUtils.generatePassword();
            }else{
                
                String proposedCommand=Levenshtein.getMinumumDistanceToComand(theInput);
                if(proposedCommand!=null){
                    MessageFormat fmt = new MessageFormat(ChatConstants.MSG_TUTORIAL_OUCH);
                    theOutput=fmt.format(new Object[] {theInput, proposedCommand });
                    }else
                    theOutput = ChatConstants.MSG_NO_SUCH_COMMAND;
            }
        } 
        return theOutput;
    }
    
}
