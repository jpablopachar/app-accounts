package com.api.hateoas.appaccounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.hateoas.appaccounts.model.Account;
import com.api.hateoas.appaccounts.repository.IAccountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {
    @Autowired
    private IAccountRepository accountRepository;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getById(Integer id) {
        return accountRepository.findById(id).get();
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }
}
