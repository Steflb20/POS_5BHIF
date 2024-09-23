package at.htlkaindorf.ex0003_onetomany;

import at.htlkaindorf.ex0003_onetomany.entities.Account;
import at.htlkaindorf.ex0003_onetomany.entities.AccountTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
public class EntityManagerTest {

    private EntityManager em;

    @Autowired
    public EntityManagerTest(
            EntityManager em
    ) {
        this.em = em;
    }

    @Test()
    @Rollback(value = false)
    public void testAccount() {
        Account account = new Account();
        account.setValue(1000);

        AccountTransaction firstTransaction = new AccountTransaction();
        firstTransaction.setAccount(account);
        firstTransaction.setText("Test Transaction");
        firstTransaction.setValue(100);
        firstTransaction.setDate(LocalDate.now());

        AccountTransaction secondTransaction = new AccountTransaction();
        secondTransaction.setAccount(account);
        secondTransaction.setText("Test Transaction 2");
        secondTransaction.setValue(200);
        secondTransaction.setDate(LocalDate.now());

        account.getTransactions().add(firstTransaction);
        account.getTransactions().add(secondTransaction);
        account.setValue(account.getValue() + firstTransaction.getValue() + secondTransaction.getValue());

        this.em.persist(account);
        // this.em.persist(accountTransaction);

        List<Account> accounts = this.em.createQuery("SELECT a FROM Account a").getResultList();
        List<AccountTransaction> accountTransactions = this.em.createQuery("SELECT at FROM AccountTransaction at").getResultList();

        TypedQuery<Double> query = this.em.createQuery(
                "SELECT SUM(value) FROM AccountTransaction at WHERE at.account.id = :account_id",
                Double.class
        );

        query.setParameter("account_id", account.getId());

        double sum = query.getSingleResult();


        assertThat(accounts.size()).isEqualTo(1);
        assertThat(accountTransactions.size()).isEqualTo(2);
        assertThat(accounts.getFirst().getValue()).isEqualTo(1300);
        assertThat(sum).isEqualTo(300);
    }

}
