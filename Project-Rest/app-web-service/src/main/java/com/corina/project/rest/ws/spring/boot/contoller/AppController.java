/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corina.project.rest.ws.spring.boot.contoller;

import com.corina.project.entity.Transaction;
import com.corina.project.repository.TransactionRepositoryIntf;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author corina
 */
@Controller
public class AppController {

    @Autowired
    TransactionRepositoryIntf transactionRepository;

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public @ResponseBody
    String showLoginPage(ModelMap model) {
        return "Processing aap1";
    }
    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public @ResponseBody
    String getTransactions(ModelMap model) {
        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList.toString();
    }
}
