package com.api.hateoas.appaccounts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.api.hateoas.appaccounts.model.Account;
import com.api.hateoas.appaccounts.repository.IAccountRepository;

@DataJpaTest
@Rollback(value = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {
    @Autowired
    private IAccountRepository accountRepository;

    @Test
    void testAddAccount() {
        Account account = new Account(222, "123456");

        Account savedAccount = accountRepository.save(account);

        Assertions.assertThat(savedAccount).isNotNull();
        Assertions.assertThat(savedAccount.getId()).isGreaterThan(0);
    }
}
