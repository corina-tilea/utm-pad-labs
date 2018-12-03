/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corina.project.entity;

import com.datastax.driver.core.LocalDate;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Objects;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 *
 * @author andrei
 */
@Table
@XStreamAlias("transaction")
public class Transaction {
    
    // de moficat daca trebuie aici - si de creat tabelul normal in cqlsh.
    
    @PrimaryKeyColumn(
      name = "account_number", 
      ordinal = 2, 
      type = PrimaryKeyType.PARTITIONED, 
      ordering = Ordering.DESCENDING)
    @XStreamAlias("accountNumber")
    private String accountNumber;

    @PrimaryKeyColumn(
    name = "transaction_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    @XStreamAlias("transactionId")
    private Integer transactionId;
    
    @Column("amount")
    @XStreamAlias("amount")
    private Double amount;
    
    @Column("owner_name")
    @XStreamAlias("ownerName")
    private String ownerName;
   
    @Column("transaction_date")
    @XStreamAlias("transaction_date")
    private LocalDate transactionDate;
    
    @XStreamAlias("transaction_format")
    @Column("transaction_format")
    private String transactionFormat;


    public Transaction() {
    }   

    public Transaction( String ownerName, String accountNumber) {
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionFormat() {
        return transactionFormat;
    }

    public void setTransactionFormat(String transactionFormat) {
        this.transactionFormat = transactionFormat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.accountNumber);
        hash = 79 * hash + Objects.hashCode(this.transactionId);
        hash = 79 * hash + Objects.hashCode(this.amount);
        hash = 79 * hash + Objects.hashCode(this.ownerName);
        hash = 79 * hash + Objects.hashCode(this.transactionDate);
        hash = 79 * hash + Objects.hashCode(this.transactionFormat);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (!Objects.equals(this.accountNumber, other.accountNumber)) {
            return false;
        }
        if (!Objects.equals(this.ownerName, other.ownerName)) {
            return false;
        }
        if (!Objects.equals(this.transactionFormat, other.transactionFormat)) {
            return false;
        }
        if (!Objects.equals(this.transactionId, other.transactionId)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.transactionDate, other.transactionDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transaction{" + "accountNumber=" + accountNumber + ", transactionId=" + transactionId + ", amount=" + amount + ", ownerName=" + ownerName + ", transactionDate=" + transactionDate + ", transactionFormat=" + transactionFormat + '}';
    }

    
    
}
