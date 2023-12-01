package com.api.hateoas.appaccounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.hateoas.appaccounts.model.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
    @Query("UPDATE Account a SET a.amount = a.amount + ?1 WHERE a.id = ?2")
    @Modifying
    void updateAmount(float amount, Integer id);
}
