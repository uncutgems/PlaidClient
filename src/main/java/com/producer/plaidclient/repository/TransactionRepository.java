package com.producer.plaidclient.repository;

import com.producer.plaidclient.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionByPlaidAccountAndDateBetween(String accountId, Date startDate, Date endDate);

    List<Transaction> findAllByPlaidAccountInAndDateBetween(List<String> plaidAccountId, Date startDate, Date endDate);

    Transaction findByPlaidTransactionId(String plaidTransactionId);

    void deleteByPlaidTransactionId(String plaidTransactionId);

}
