package com.api.hateoas.appaccounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.hateoas.appaccounts.model.Account;
import com.api.hateoas.appaccounts.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> listAccounts() {
        List<Account> accounts = accountService.getAll();

        if (accounts.isEmpty())
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Integer id) {
        try {
            Account account = accountService.getById(id);

            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.save(account);

        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Account> editAccount(@RequestBody Account account) {
        Account editedAccount = accountService.save(account);

        return new ResponseEntity<>(editedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Integer id) {
        try {
            accountService.delete(id);

            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
