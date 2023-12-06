package com.api.hateoas.appaccounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.hateoas.appaccounts.exception.AccountNotFoundException;
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

    public void delete(Integer id) throws AccountNotFoundException {
        if (!accountRepository.existsById(id))
            throw new AccountNotFoundException("La cuenta con id " + id + " no existe");

        accountRepository.deleteById(id);
    }

    public Account deposit(float amount, Integer id) {
        accountRepository.updateAmount(amount, id);

        return accountRepository.findById(id).get();
    }

    public Account withdraw(float amount, Integer id) {
        accountRepository.updateAmount(-amount, id);

        return accountRepository.findById(id).get();
    }
}
