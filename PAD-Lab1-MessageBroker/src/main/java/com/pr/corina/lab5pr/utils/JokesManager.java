/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pr.corina.lab5pr.utils;

import com.pr.corina.lab5pr.utils.ChatConstants;
import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author corina
 */
public class JokesManager {
    
    String []jokes;

    public JokesManager() {
        jokes=new String[5];
        jokes[0]="Can a kangaroo jump higher than a house?"+ChatConstants.PROTOCOL_NEW_LINE+
                 "Of course, a house doesn’t jump at all.";
        jokes[1]="Doctor: I'm sorry but you suffer from a terminal illness and have only 10 to live."+ChatConstants.PROTOCOL_NEW_LINE+
                 "Patient: What do you mean, 10? 10 what? Months? Weeks?!"+ChatConstants.PROTOCOL_NEW_LINE+
                 "Doctor: Nine...";
        jokes[2]="A man asks a farmer near a field, ”Sorry sir, would you mind if I crossed your field instead of going around it? You see, I have to catch the 4:23 train”."+ChatConstants.PROTOCOL_NEW_LINE+
                 "The farmer says, “Sure, go right ahead. And if my bull sees you, you’ll even catch the 4:11 one.”";
        jokes[3]="Anton, do you think I’m a bad mother?"+ChatConstants.PROTOCOL_NEW_LINE+
                 "My name is Paul.";
        jokes[4]="My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.";
            
    }
    
    public String getRandomJoke(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        return jokes[randomNum];
    }
    
    
}



