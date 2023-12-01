package com.api.hateoas.appaccounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.hateoas.appaccounts.model.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

}
