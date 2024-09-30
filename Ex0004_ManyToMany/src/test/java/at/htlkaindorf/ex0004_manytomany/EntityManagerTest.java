package at.htlkaindorf.ex0004_manytomany;

import at.htlkaindorf.ex0004_manytomany.entities.Account;
import at.htlkaindorf.ex0004_manytomany.entities.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    @Test
    @Rollback(value = false)
    public void addAccounts() {
        Customer firstCustomer = new Customer();
        firstCustomer.setFirstname("Florian");
        firstCustomer.setLastname("Sternad");
        firstCustomer.setBirthdate(LocalDate.now());
        firstCustomer.setAccounts(new ArrayList<>());

        Customer secondCustomer = new Customer();
        secondCustomer.setFirstname("Lara");
        secondCustomer.setLastname("Zimmermann");
        secondCustomer.setBirthdate(LocalDate.now());
        secondCustomer.setAccounts(new ArrayList<>());

        Account firstAccount = new Account();
        firstAccount.setValue(100);
        firstAccount.setCustomers(new ArrayList<>());

        Account secondAccount = new Account();
        secondAccount.setValue(200);
        secondAccount.setCustomers(new ArrayList<>());

        firstAccount.getCustomers().add(firstCustomer);
        firstAccount.getCustomers().add(secondCustomer);

        firstCustomer.getAccounts().add(firstAccount);
        secondCustomer.getAccounts().add(firstAccount);

        secondAccount.getCustomers().add(firstCustomer);
        firstCustomer.getAccounts().add(secondAccount);

        this.em.persist(firstCustomer);
        this.em.persist(secondCustomer);
        this.em.persist(firstAccount);
        this.em.persist(secondAccount);

        TypedQuery<Account> customerAccountQuery = this.em.createQuery(
                "SELECT c.accounts FROM Customer c WHERE c.id = :account_id",
                Account.class
        );

        customerAccountQuery.setParameter("account_id", 1);

        List<Account> firstCustomerAccounts = customerAccountQuery.getResultList();

        customerAccountQuery.setParameter("account_id", 2);

        List<Account> secondCustomerAccounts = customerAccountQuery.getResultList();

        assertThat(firstCustomerAccounts.size()).isEqualTo(2);
        assertThat(secondCustomerAccounts.size()).isEqualTo(1);
    }
}
