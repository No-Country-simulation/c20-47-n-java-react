package com.FlowBanck.repository;

import com.FlowBanck.entity.BankAccount;
import com.FlowBanck.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByCbu(String cbu);

    Optional<BankAccount> findByAlias(String alias);

    BankAccount findByUserEntity(UserEntity user);
}
