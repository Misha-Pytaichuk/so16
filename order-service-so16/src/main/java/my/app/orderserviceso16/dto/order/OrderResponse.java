package my.app.orderserviceso16.dto.order;

import lombok.*;
import my.app.orderserviceso16.dto.item.OrderLineItemsResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String orderNumber;
    private String orderName;
    private LocalDateTime createdAt;
    private List<OrderLineItemsResponseDto> orderLineItemsResponseDto;
}
