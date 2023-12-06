package com.api.hateoas.appaccounts.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.hateoas.appaccounts.model.Account;
import com.api.hateoas.appaccounts.model.Amount;
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

        for (Account account : accounts) {
            account.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());

            account.add(linkTo(methodOn(AccountController.class).listAccounts()).withRel(IanaLinkRelations.COLLECTION));
        }

        CollectionModel<Account> model = CollectionModel.of(accounts);

        model.add(linkTo(methodOn(AccountController.class).listAccounts()).withSelfRel());

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Integer id) {
        try {
            Account account = accountService.getById(id);

            account.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());

            account.add(linkTo(methodOn(AccountController.class).listAccounts()).withRel(IanaLinkRelations.COLLECTION));

            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.save(account);

        newAccount.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());

        newAccount.add(linkTo(methodOn(AccountController.class).listAccounts()).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(linkTo(methodOn(AccountController.class).getAccount(newAccount.getId())).toUri())
                .body(newAccount);
    }

    @PutMapping
    public ResponseEntity<Account> editAccount(@RequestBody Account account) {
        Account editedAccount = accountService.save(account);

        editedAccount.add(linkTo(methodOn(AccountController.class).getAccount(editedAccount.getId())).withSelfRel());

        editedAccount.add(linkTo(methodOn(AccountController.class).listAccounts()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(editedAccount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<Account> depositMoney(@PathVariable Integer id, @RequestBody Amount amount) {
        Account editedAccount = accountService.deposit(amount.getAmount(), id);

        editedAccount.add(linkTo(methodOn(AccountController.class).getAccount(editedAccount.getId())).withSelfRel());

        editedAccount.add(linkTo(methodOn(AccountController.class).depositMoney(editedAccount.getId(), null)).withRel("deposits"));

        editedAccount.add(linkTo(methodOn(AccountController.class).listAccounts()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(editedAccount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdrawMoney(@PathVariable Integer id, @RequestBody Amount amount) {
        Account editedAccount = accountService.withdraw(amount.getAmount(), id);

        editedAccount.add(linkTo(methodOn(AccountController.class).getAccount(editedAccount.getId())).withSelfRel());

        editedAccount.add(linkTo(methodOn(AccountController.class).depositMoney(editedAccount.getId(), null)).withRel("deposits"));

        editedAccount.add(linkTo(methodOn(AccountController.class).withdrawMoney(editedAccount.getId(), null)).withRel("withdraws"));

        editedAccount.add(linkTo(methodOn(AccountController.class).listAccounts()).withRel(IanaLinkRelations.COLLECTION));

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
