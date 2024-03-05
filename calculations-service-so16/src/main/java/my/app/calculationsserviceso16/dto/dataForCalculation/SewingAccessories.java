package my.app.calculationsserviceso16.dto.dataForCalculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SewingAccessories {
    private double zipperCost;
    private int zipperCount;
    private double threadSpoolCost;
    private int threadSpoolCount;
    private double buttonsCost;
    private int buttonsCount;
}
