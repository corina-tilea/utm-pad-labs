/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corina.project.rest.ws.spring.boot.contoller;

import com.corina.project.entity.Transaction;
import com.corina.project.repository.TransactionRepositoryIntf;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author corina
 */
@Controller
public class AppController {

    private static final Logger LOG = Logger.getLogger(AppController.class.getName());

    
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
    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String findTransactionById(@PathVariable Integer id) {
        LOG.info("### Web Service Method - findTransactionById called for user id = "+id);
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        //Transaction transaction = transactionOptional.get();
        //return transaction.toString();
        return "from transaction 1";
    }
}
