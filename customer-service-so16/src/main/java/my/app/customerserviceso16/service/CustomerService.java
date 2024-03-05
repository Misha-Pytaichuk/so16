package my.app.customerserviceso16.service;

import lombok.RequiredArgsConstructor;
import my.app.customerserviceso16.dto.customer.CustomerResponse;
import my.app.customerserviceso16.dto.customer.create_update.CustomerRequest;
import my.app.customerserviceso16.dto.customer.search.CustomerSearchRequest;
import my.app.customerserviceso16.dto.customer.search.CustomerSearchResponse;
import my.app.customerserviceso16.dto.order.OrderResponse;
import my.app.customerserviceso16.model.Customer;
import my.app.customerserviceso16.repository.CustomerRepository;
import my.app.customerserviceso16.exception.FindException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final WebClient.Builder webClientBuilder;
    private final CustomerRepository customerRepository;

    @Transactional
    public void createCustomer(CustomerRequest customerRequest){
        customerRepository.save(mapCustomerToPojo(customerRequest));
    }

    @Transactional
    public void updateCustomer(CustomerRequest customerRequest){
        Customer customer = findCustomer(customerRequest.getTelephoneNumber());

        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setTelephoneNumber("+38" + customerRequest.getTelephoneNumber());

        customerRepository.save(customer);
    }

    @Transactional
    public void delete(CustomerSearchRequest customerSearchRequest){
        Customer customer = findCustomer(customerSearchRequest.getTelephoneNumber());

        System.out.println(customer.getId());

        webClientBuilder.build().delete()
                .uri("http://localhost:8081/api/v1/order/byCustomer/{customerId}", customer.getId())
                .retrieve()
                .toBodilessEntity()
                .block();

        customerRepository.delete(customer);
    }

    public CustomerSearchResponse findCustomerForResponse(String telephoneNumber){

        Customer customer = findCustomer(telephoneNumber);

        List<OrderResponse> orderResponseList = webClientBuilder.build().get()
                .uri("http://localhost:8081", uriBuilder -> uriBuilder
                        .path("/api/v1/order/{customerId}")
                        .build(customer.getId()))
                .retrieve()
                .bodyToFlux(OrderResponse.class)
                .collectList()
                .block();

        CustomerSearchResponse customerSearchResponse = (CustomerSearchResponse) mapCustomerToDto(customer, CustomerSearchResponse.class);
        customerSearchResponse.setOrder(orderResponseList);

        return customerSearchResponse;
    }

    private Customer findCustomer(String telephoneNumber){
        return customerRepository
                .findCustomerByTelephoneNumber("+38" + telephoneNumber)
                .orElseThrow(()-> new FindException("telephoneNumber - Клієнта з таким номером телефону не існує;"));
    }

    public CustomerResponse findCustomer(Long id){
        Customer customer = customerRepository
                                        .findById(id)
                                        .orElseThrow(()-> new FindException("id - Клієнта з таким id не інсує;"));

        return (CustomerResponse) mapCustomerToDto(customer, CustomerResponse.class);
    }

    public Boolean customerIsPresent(Long id){

        //if(!isPresent) throw new FindException("id - Клієнта з таким id не інсує;");
        return customerRepository.findById(id).isPresent();
    }

    private Object mapCustomerToDto(Customer customer, Class<?> clazz){
        if(clazz.equals(CustomerResponse.class)){
            return CustomerResponse.builder()
                    .name(customer.getName())
                    .surname(customer.getSurname())
                    .telephoneNumber(customer.getTelephoneNumber())
                    .build();
        } else {
            return CustomerSearchResponse.builder()
                    .name(customer.getName())
                    .surname(customer.getSurname())
                    .telephoneNumber(customer.getTelephoneNumber())
                    .build();
        }
    }

    private Customer mapCustomerToPojo(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName().trim())
                .surname(customerRequest.getSurname().trim())
                .telephoneNumber("+38" + customerRequest.getTelephoneNumber().trim())
                .build();
    }
}
