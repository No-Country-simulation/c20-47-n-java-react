package com.FlowBanck.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "bankAccounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String accountNumber;
    private String cbu;
    private String alias;
    private BigDecimal amount;
    private String accountType;


    @ManyToMany(fetch = FetchType.EAGER, targetEntity = State.class, cascade = CascadeType.PERSIST)
    @JoinTable(name ="bankAccount_state", joinColumns = @JoinColumn(name="bankAccount_id"), inverseJoinColumns = @JoinColumn(name = "state_id"))
    private Set<State> state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
