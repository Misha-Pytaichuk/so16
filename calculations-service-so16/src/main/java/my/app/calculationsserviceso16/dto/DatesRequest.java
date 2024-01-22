package my.app.calculationsserviceso16.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatesRequest {
    private String fromDate;
    private String toDate;
}
