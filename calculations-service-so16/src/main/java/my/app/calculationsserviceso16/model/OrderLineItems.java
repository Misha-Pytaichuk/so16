package my.app.calculationsserviceso16.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Поле не може бути пустим")
    private String skuCode;
    @NotBlank(message = "Поле не може бути пустим")
    private String itemName;
    @Min(value = 0, message = "Ціна не може бути меньше або дорівнювати нулю")
    private BigDecimal price;
    @Min(value = 0, message = "Кількість не може бути меньше або дорівнювати нулю")
    private Integer quantity;
}
