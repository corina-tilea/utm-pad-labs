/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corina.project.rest.ws.spring.boot.contoller;

import com.corina.project.rest.ws.spring.boot.client.Client;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author corina
 */
@Controller
public class AppController {

    @Autowired
    private Client client;

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public @ResponseBody 
        String showLoginPage(ModelMap model) throws RestClientException, IOException {
        return client.getWebService("/process");
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public @ResponseBody 
        String getTransactions(ModelMap model) throws RestClientException, IOException {
        return client.getWebService("/transactions");
    }
}
