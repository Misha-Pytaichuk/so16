package my.app.orderserviceso16.dto.customer.main_response;

import lombok.*;
import my.app.orderserviceso16.dto.order.OrderResponse;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String name;
    private String surname;
    private String telephoneNumber;
    private OrderResponse order;
}
