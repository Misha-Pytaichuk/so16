package my.app.customerserviceso16.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsRequestDto {
    @NotBlank(message = "Поле не може бути пустим")
    private String skuCode;
    @NotBlank(message = "Поле не може бути пустим")
    private String itemName;
    @Min(value = 0, message = "Ціна не може бути меньше або дорівнювати нулю")
    private BigDecimal price;
    @Min(value = 0, message = "Кількість не може бути меньше або дорівнювати нулю")
    private Integer quantity;
}
