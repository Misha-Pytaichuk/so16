package my.app.orderserviceso16.dto.order;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import my.app.orderserviceso16.dto.item.OrderLineItemsRequestDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long customerId;
    private List<OrderLineItemsRequestDto> orderLineItemsRequestDto;
}
