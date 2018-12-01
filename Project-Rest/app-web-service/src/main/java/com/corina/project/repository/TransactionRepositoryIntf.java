/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corina.project.repository;

import com.corina.project.entity.Transaction;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author corina
 */

@Repository
public interface TransactionRepositoryIntf extends CassandraRepository<Transaction, UUID> {
    
}
