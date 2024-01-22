package my.app.customerserviceso16.repository;

import my.app.customerserviceso16.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByTelephoneNumber(String telephoneNumber);
}
