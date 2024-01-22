package my.app.calculationsserviceso16.service;

import lombok.RequiredArgsConstructor;
import my.app.calculationsserviceso16.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public Double getPrice(LocalDateTime dateFrom, LocalDateTime dateTo){
        return orderRepository.getPrice(dateFrom, dateTo);
    }
}
