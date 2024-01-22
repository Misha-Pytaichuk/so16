package my.app.orderserviceso16.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import my.app.orderserviceso16.dto.customer.main_response.Response;
import my.app.orderserviceso16.dto.item.OrderLineItemsRequestDto;
import my.app.orderserviceso16.dto.order.OrderRequest;
import my.app.orderserviceso16.dto.order.OrderResponse;
import my.app.orderserviceso16.service.OrderService;
import my.app.orderserviceso16.util.exception.BindingExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final BindingExceptionHandler bindingExceptionHandler;

    @GetMapping("/{customerId}") //Не трогать
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersByCustomer(@PathVariable("customerId") Long customerId){
        return orderService.findOrders(customerId);
    }

    @GetMapping("/get/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Response getOrder(@PathVariable("orderNumber") String orderNumber){
        return orderService.getOrder(orderNumber);
    }

    @GetMapping("/{page}/{sort}") //"ASC"/ "DESC"
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders(@PathVariable("page") Integer page, @PathVariable("sort") String sort){
        return orderService.getOrders(page, sort);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody @Valid OrderRequest orderRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        orderService.save(orderRequest);
        return "Order placed successfully!";
    }

    @DeleteMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteOrder(@PathVariable("orderNumber") String orderNumber){
        orderService.deleteOrder(orderNumber);

        return "Order deleted successfully!";
    }

    @DeleteMapping("/byCustomer/{customerId}") // Не трогать
    @ResponseStatus(HttpStatus.OK)
    public String deleteOrder(@PathVariable("customerId") Long customerId){
        System.out.println("in order" + customerId);
        orderService.deleteOrderByCustomer(customerId);

        return "Order deleted successfully!";
    }

    @PatchMapping("/{orderNumber}/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateOrderItem(@PathVariable("orderNumber") String orderNumber,
                                  @PathVariable("itemId") Long itemId,
                                  @RequestBody @Valid OrderLineItemsRequestDto orderLineItemsRequestDto,
                                  BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        orderService.updateItem(orderNumber, itemId, orderLineItemsRequestDto);

        return "Order item updated successfully!";
    }

    @PatchMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public String addItemToOrder(@PathVariable("orderNumber") String orderNumber,
                                 @RequestBody @Valid OrderLineItemsRequestDto orderLineItemsRequestDto,
                                 BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        orderService.addItemToOrder(orderNumber, orderLineItemsRequestDto);

        return "Order item created successfully!";
    }

    @DeleteMapping("/{orderNumber}/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteItemFromOrder(@PathVariable("orderNumber") String orderNumber,
                                      @PathVariable("itemId") Long itemId){

        orderService.deleteItemFromOrder(orderNumber, itemId);

        return "Order item deleted successfully!";
    }
}
