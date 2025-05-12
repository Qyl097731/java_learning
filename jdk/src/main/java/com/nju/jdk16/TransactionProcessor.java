package com.nju.jdk16;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date 2024/6/26 0:05
 * @author: qyl
 */

public class TransactionProcessor {
    // Local record for transaction
    record Transaction(TransactionType type, double amount, TransactionStatus status) {}
    // Local enum for transaction types
    enum TransactionType {
        DEPOSIT, WITHDRAWAL
    }

    // Local enum for transaction status
    enum TransactionStatus {
        PENDING, COMPLETED, FAILED
    }

    // Local interface for processing transactions
    interface TransactionHandler {
        void handle(Transaction transaction);
    }

    public void processTransactions(List<Transaction> transactions) {
        // Implementing the local interface
        TransactionHandler handler = transaction -> {
            if (transaction.type() == TransactionType.DEPOSIT) {
                System.out.println("Processing deposit of $" + transaction.amount());
            } else if (transaction.type() == TransactionType.WITHDRAWAL) {
                System.out.println("Processing withdrawal of $" + transaction.amount());
            }
        };

        // Process each transaction
        for (Transaction transaction : transactions) {
            handler.handle(transaction);
            // Assuming all transactions are completed for this example
            transaction = new Transaction(transaction.type(), transaction.amount(), TransactionStatus.COMPLETED);
            System.out.println("Transaction status: " + transaction.status());
        }
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(TransactionType.DEPOSIT, 100.0, TransactionStatus.PENDING));
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, 50.0, TransactionStatus.PENDING));

        new TransactionProcessor().processTransactions(transactions);
    }
}
