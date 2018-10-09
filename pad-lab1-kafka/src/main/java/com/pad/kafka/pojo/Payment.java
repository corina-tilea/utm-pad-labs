/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pad.kafka.pojo;

import java.util.Date;

/**
 *
 * @author corina
 */
public class Payment {
    
    private Long id;
    private Date date;
    private Double amount;
    private String bank;
    private String payerFirstName;
    private String payerLastName;

    public Payment() {
    }

    public Payment(Long id, Date date, Double amount, String bank, String payerFirstName, String payerLastName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.bank = bank;
        this.payerFirstName = payerFirstName;
        this.payerLastName = payerLastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPayerFirstName() {
        return payerFirstName;
    }

    public void setPayerFirstName(String payerFirstName) {
        this.payerFirstName = payerFirstName;
    }

    public String getPayerLastName() {
        return payerLastName;
    }

    public void setPayerLastName(String payerLastName) {
        this.payerLastName = payerLastName;
    }
    
    
}
