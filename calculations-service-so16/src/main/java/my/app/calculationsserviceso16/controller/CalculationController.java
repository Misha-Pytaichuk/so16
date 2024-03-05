package my.app.calculationsserviceso16.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import my.app.calculationsserviceso16.dto.DatesRequest;
import my.app.calculationsserviceso16.dto.dataForCalculation.AllDataRequest;
import my.app.calculationsserviceso16.dto.dataForCalculation.SewingAccessories;
import my.app.calculationsserviceso16.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calculation")
public class CalculationController {
    private final OrderService orderService;

    @GetMapping("/range")
    @ResponseStatus(HttpStatus.OK)
    public void getCalculationsForDateDiapason(@RequestBody DatesRequest datesRequest, BindingResult bindingResult){

        int[] datesFrom = Arrays.stream(datesRequest.getFromDate().split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] datesTo = Arrays.stream(datesRequest.getToDate().split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();

        //dates format yyyy-mm-dd-(time)
        LocalDateTime dateTimeFrom = LocalDateTime.of(datesFrom[0], datesFrom[1], datesFrom[2], 0, 0, 0);
        LocalDateTime dateTimeTo = LocalDateTime.of(datesTo[0], datesTo[1], datesTo[2]+1, 0, 0, 0);

        if(dateTimeFrom.isAfter(dateTimeTo)){
            throw new ValidationException("date - dateFrom cannot be greater than dateTo");
        }

        System.out.println(dateTimeFrom);
        System.out.println(dateTimeTo);
        System.out.println();
        System.out.println(orderService.getPrice(dateTimeFrom, dateTimeTo));
    }

    @GetMapping("/{days}")
    @ResponseStatus(HttpStatus.OK)
    public void getCalculationsForLastDays(@PathVariable("days") Integer days){

        //dates format yyyy-mm-dd-(time)
        LocalDateTime currentDate = LocalDateTime.now().plusDays(1);
        LocalDateTime dateTimeFrom = currentDate.minusDays(days);

        if(days <= 0){
            throw new ValidationException("date - days count should be greater than 0");
        }

        System.out.println(currentDate);
        System.out.println(dateTimeFrom);
        System.out.println();
        System.out.println(orderService.getPrice(dateTimeFrom, currentDate));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Double calculationOfCost(@RequestBody AllDataRequest allDataRequest){
        double clothCost = allDataRequest.getClothCost() * allDataRequest.getSmCloth();
        double liningCost = allDataRequest.getLiningCost() * allDataRequest.getSmLining();
        double seamstressCost = allDataRequest.getSeamstressCost();
        double packagingCost = allDataRequest.getPackagingCost();

        SewingAccessories accessories = allDataRequest.getSewingAccessories();
        double zipperCost = accessories.getZipperCost() * accessories.getZipperCount();
        double threadSpoolCost = accessories.getThreadSpoolCost() * accessories.getThreadSpoolCount();
        double buttonsCost = accessories.getButtonsCost() * accessories.getButtonsCount();

        double cost = (clothCost + liningCost + seamstressCost + packagingCost + zipperCost + threadSpoolCost + buttonsCost);
        double totalCost = (cost * allDataRequest.getMarkup() / 100) + cost;
        System.out.println(totalCost);
        return totalCost;
    }
}
