package my.app.customerserviceso16.dto.customer.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.app.customerserviceso16.dto.order.OrderResponse;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSearchResponse {
    private String name;
    private String surname;
    private String telephoneNumber;
    private List<OrderResponse> order;
}
