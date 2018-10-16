/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pr.corina.lab5pr.utils;

import com.pr.corina.lab5pr.utils.ChatConstants;

/**
 *
 * @author corina
 */
public class TextGenerator {
    
    
    public static String getWelcomeText(){
        StringBuilder sb=new StringBuilder("Dear user, welcome to our server.");
        sb.append(" In order to find out our commands, please call /help command.");
        return sb.toString();
    }
    
    public static String getHelpText(){
        StringBuilder sb=new StringBuilder(ChatConstants.MSG_SUPPORTED_COMMANDS);
        sb.append("\n").append(ChatConstants.MSG_HELP);
        sb.append("\n").append(ChatConstants.MSG_HELLO_PARAM);
        sb.append("\n").append(ChatConstants.MSG_QUIT);
        return sb.toString();
    }
    public static String getHelloParamText(String param){
        StringBuilder sb=new StringBuilder(ChatConstants.WELCOME);
        sb.append(" ").append(param);
        sb.append("\n").append(ChatConstants.MSG_PARAMETER);
        return sb.toString();
    }
    
}
