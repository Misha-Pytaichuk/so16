package my.app.customerserviceso16.dto.order;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.app.customerserviceso16.dto.item.OrderLineItemsRequestDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @Pattern(regexp = "0\\d{9}")
    private String telephoneNumber;
    private List<OrderLineItemsRequestDto> orderLineItemsRequestDto;
}
