package at.htlkaindorf.ex002_onetoone;

import at.htlkaindorf.ex002_onetoone.entities.Address;
import at.htlkaindorf.ex002_onetoone.entities.Customer;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional(dontRollbackOn = {})
public class EntityManagerTest {
    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public EntityManagerTest(
            EntityManager em
    ) {
        this.entityManager = em;
    }

    @Test()
    public void saveData() {
        Address address = new Address();
        address.setStreet("Musterstrasse");
        address.setHouseNumber("1");
        address.setTown("Musterort");
        address.setZipCode("1234");

        Customer customer = new Customer();
        customer.setFirstname("Lara");
        customer.setSurname("Zimmermann");
        customer.setAddress(address);

        address.setCustomer(customer);

        this.entityManager.persist(address);
        this.entityManager.persist(customer);

        List<Address> addresses = this.entityManager.createQuery("SELECT a FROM Address a").getResultList();
        addresses.forEach(a -> this.logger.info("{}", a));

        List<Customer> customers = this.entityManager.createQuery("SELECT c FROM Customer c").getResultList();
        customers.forEach(c -> this.logger.info("{}", c));

        assertThat(addresses.size()).isEqualTo(1);
        assertThat(customers.size()).isEqualTo(1);

    }
}
