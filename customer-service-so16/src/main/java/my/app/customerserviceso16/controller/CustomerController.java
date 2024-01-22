package my.app.customerserviceso16.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

import my.app.customerserviceso16.dto.customer.CustomerResponse;
import my.app.customerserviceso16.dto.customer.create_update.CustomerRequest;
import my.app.customerserviceso16.dto.customer.search.CustomerSearchRequest;
import my.app.customerserviceso16.dto.customer.search.CustomerSearchResponse;
import my.app.customerserviceso16.exception.BindingExceptionHandler;
import my.app.customerserviceso16.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final BindingExceptionHandler bindingExceptionHandler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public CustomerResponse findCustomer(@PathVariable("id") Long id){
        return customerService.findCustomer(id);
    }

    @GetMapping("/isPresent/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean customerIsPresent(@PathVariable("id") Long id){
        System.out.println(id);
        return customerService.customerIsPresent(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.FOUND)
    public CustomerSearchResponse findCustomer(@RequestBody @Valid CustomerSearchRequest customerSearchRequest, BindingResult bindingResult) {
        String telephoneNumber = customerSearchRequest.getTelephoneNumber().trim();
        System.out.println(telephoneNumber);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        return customerService.findCustomerForResponse(telephoneNumber);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody @Valid CustomerRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        customerService.createCustomer(request);

        return "Customer created successfully";
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody @Valid CustomerRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }
        customerService.updateCustomer(request);
        return "Customer updated successfully";
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public String delete(@RequestBody @Valid CustomerSearchRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        customerService.delete(request);

        return "Customer deleted successfully";
    }

}
