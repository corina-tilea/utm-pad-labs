/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pr.corina.lab5pr.utils;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

/**
 *
 * @author corina
 */
public class AppUtils {
    
    
    public static String generateAlphaString(){
        RandomStringGenerator randomStringGenerator =new RandomStringGenerator.Builder()
                    .withinRange('a', 'z')
                    .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                    .build();
        return  randomStringGenerator.generate(12);
    }
}
