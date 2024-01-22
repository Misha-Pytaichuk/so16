package my.app.orderserviceso16.repository;

import my.app.orderserviceso16.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByOrderNumber(String orderNumber);
    List<Order> findOrdersByCustomerId(Long customerId);

    List<Order> findOrdersByCreatedAtBetween(LocalDateTime after, LocalDateTime before);
}
