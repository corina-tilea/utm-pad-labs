/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corina.project.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Objects;
import java.util.UUID;
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
public class Transaction {
    
    
    @PrimaryKeyColumn(
      name = "id", 
      ordinal = 2, 
      type = PrimaryKeyType.CLUSTERED, 
      ordering = Ordering.DESCENDING)
    private UUID id;

    @PrimaryKeyColumn(
    name = "transaction_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String transactionId;
    
    @Column("owner_name")
    private String ownerName;
    
    @Column("account_number")
    private String accountNumber;
    
    @Column("transaction_format")
    private String transactionFormat;


    public Transaction() {
    }   

    public Transaction( String ownerName, String accountNumber) {
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.ownerName);
        hash = 31 * hash + Objects.hashCode(this.accountNumber);
        hash = 31 * hash + Objects.hashCode(this.transactionFormat);
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
        if (!Objects.equals(this.ownerName, other.ownerName)) {
            return false;
        }
        if (!Objects.equals(this.accountNumber, other.accountNumber)) {
            return false;
        }
        if (!Objects.equals(this.transactionFormat, other.transactionFormat)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", ownerName=" + ownerName + ", accountNumber=" + accountNumber + ", transactionFormat=" + transactionFormat + '}';
    }
    
}
