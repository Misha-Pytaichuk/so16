package my.app.calculationsserviceso16.dto.dataForCalculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SewingAccessories {
    private double zipper;
    private int zipperCount;
    private double threadSpool;
    private int threadSpoolCount;
    private double buttons;
    private int buttonsCount;
}
