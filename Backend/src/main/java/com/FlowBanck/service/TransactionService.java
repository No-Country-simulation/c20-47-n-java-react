package com.FlowBanck.service;

import com.FlowBanck.dto.TransactionDto;
import com.FlowBanck.entity.BankAccount;
import com.FlowBanck.entity.EnumTransaction;
import com.FlowBanck.entity.Transaction;
import com.FlowBanck.entity.TransactionType;
import com.FlowBanck.repository.BankAccountRepository;
import com.FlowBanck.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Transactional
    public void createTransaction(TransactionDto transactionDto, BankAccount accountOrigin){
        Optional<BankAccount> accountDestination = findAccountByIdentifier(transactionDto.getAccountIdentifier(),transactionDto.getMethod());
        if (accountDestination.isEmpty()){
            throw new IllegalArgumentException("Cuenta destino no encontrada");
        }
        if (accountOrigin.getAmount().compareTo(transactionDto.getAmount()) < 0){
            throw new IllegalArgumentException("Saldo insuficiente");
        }

       /* TransactionType transactionType = TransactionType.builder()
                .transaction(EnumTransaction.TRANSFERENCIA)
                .build();*/
        Transaction transaction = Transaction.builder()
                .accoountOrigin(accountOrigin)
                .accountDestination(accountDestination.get())
                .amount(transactionDto.getAmount())
                .typeTransaction(EnumTransaction.TRANSFERENCIA)
                .build();

        transactionRepository.save(transaction);
        bankAccountRepository.save(accountOrigin);
        bankAccountRepository.save(accountDestination.get());
    }

    private Optional<BankAccount> findAccountByIdentifier(String identifier,String method){
        return method.equalsIgnoreCase("CBU")? bankAccountRepository.findByCbu(identifier):
                bankAccountRepository.findByAlias(identifier);
    }


}
