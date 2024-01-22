package my.app.calculationsserviceso16.dto.dataForCalculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllDataRequest {
    private double cloth; // тань
    private double smCloth; // кол-во ткани(в м^2)
    private double lining; //подкладка
    private double smLining; // кол-во подкладки(в м^2)
    private double seamstress; // швея
    private double packaging; // упаковка
    private double markup; // наценка
    private SewingAccessories sewingAccessories; // фурнитура
}
