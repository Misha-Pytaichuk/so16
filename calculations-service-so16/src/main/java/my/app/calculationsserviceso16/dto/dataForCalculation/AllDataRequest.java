package my.app.calculationsserviceso16.dto.dataForCalculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllDataRequest {
    private double clothCost; // тань
    private double smCloth; // кол-во ткани(в м^2)
    private double liningCost; //подкладка
    private double smLining; // кол-во подкладки(в м^2)
    private double seamstressCost; // швея
    private double packagingCost; // упаковка
    private double markup; // наценка
    private SewingAccessories sewingAccessories; // фурнитура
}
