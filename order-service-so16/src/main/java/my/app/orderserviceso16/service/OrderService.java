package my.app.orderserviceso16.service;

import lombok.RequiredArgsConstructor;
import my.app.orderserviceso16.dto.customer.CustomerResponse;
import my.app.orderserviceso16.dto.customer.main_response.Response;
import my.app.orderserviceso16.dto.item.OrderLineItemsRequestDto;
import my.app.orderserviceso16.dto.item.OrderLineItemsResponseDto;
import my.app.orderserviceso16.dto.order.OrderRequest;
import my.app.orderserviceso16.dto.order.OrderResponse;
import my.app.orderserviceso16.model.Order;
import my.app.orderserviceso16.model.OrderLineItems;
import my.app.orderserviceso16.repository.OrderRepository;
import my.app.orderserviceso16.util.exception.FindException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public Response getOrder(String orderNumber){
        Order order = findOrder(orderNumber);

        CustomerResponse customerResponse = webClientBuilder.build().get()
                .uri("http://localhost:8080", uriBuilder -> uriBuilder
                        .path("/api/v1/customer/{customerId}")
                        .build(order.getCustomerId()))
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();

        OrderResponse orderResponse = mapToOrderResponse(findOrder(orderNumber));
        return buildResponse(Objects.requireNonNull(customerResponse), orderResponse);
    }

    @Transactional
    public void save(OrderRequest orderRequest) {

        Boolean isPresent = webClientBuilder.build().get()
                .uri("http://localhost:8080", uriBuilder -> uriBuilder
                        .path("/api/v1/customer/isPresent/{customerId}")
                        .build(orderRequest.getCustomerId()))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if(Boolean.FALSE.equals(isPresent)) throw new FindException("id - Користувача з таким id не інсує");

        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsRequestDto()
                .stream()
                .map(this::mapToPojo)
                .toList();

        order.setOrderName(createNameForOrder(orderLineItems));
        order.setOrderLineItems(orderLineItems);
        order.setCreatedAt(LocalDateTime.now());
        order.setCustomerId(orderRequest.getCustomerId());

        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(String orderNumber){
        Order order = findOrder(orderNumber);

        orderRepository.delete(order);
    }

    @Transactional
    public void deleteOrderByCustomer(Long customerId) {
        List<Order> orderList = orderRepository.findOrdersByCustomerId(customerId);
        for (Order order: orderList){
            System.out.println(order.getOrderName());
        }
        orderRepository.deleteAll(orderList);
    }

    @Transactional
    public void updateItem(String orderNumber, Long itemId, OrderLineItemsRequestDto orderLineItemsRequestDto){
        Order order = findOrder(orderNumber);
        for (OrderLineItems oli: order.getOrderLineItems()) {
            if(oli.getId().equals(itemId)){
                oli.setSkuCode(orderLineItemsRequestDto.getSkuCode());
                oli.setItemName(orderLineItemsRequestDto.getItemName());
                oli.setPrice(orderLineItemsRequestDto.getPrice());
                oli.setQuantity(orderLineItemsRequestDto.getQuantity());
            }
        }
        order.setOrderName(createNameForOrder(order.getOrderLineItems()));
        orderRepository.save(order);
    }

    @Transactional
    public void addItemToOrder(String orderNumber, OrderLineItemsRequestDto orderLineItemsRequestDto){
        Order order = findOrder(orderNumber);
        order.getOrderLineItems().add(mapToPojo(orderLineItemsRequestDto));
        order.setOrderName(createNameForOrder(order.getOrderLineItems()));
        orderRepository.save(order);
    }

    @Transactional
    public void deleteItemFromOrder(String orderNumber, Long itemId){
        Order order = findOrder(orderNumber);

        order.getOrderLineItems().removeIf(orderLineItems -> orderLineItems.getId().equals(itemId));
        order.setOrderName(createNameForOrder(order.getOrderLineItems()));

        orderRepository.save(order);
    }

    public List<OrderResponse> getOrders(Integer page, String sort){
        List<Order> orderList;

        if(sort.equals("ASC"))
            orderList = orderRepository.findAll(PageRequest.of(page, 30, Sort.by("createdAt").ascending())).getContent();
        else
            orderList = orderRepository.findAll(PageRequest.of(page, 30, Sort.by("createdAt").descending())).getContent();

        return orderList.stream().map(this::mapToOrderResponse).toList();
    }

    public List<OrderResponse> getOrders(String fromDate, String toDate){
        //List<Order> orderList = orderRepository.findOrdersByCreatedAtBetween(fromDate, toDate);


        //return orderList.stream().map(this::mapToOrderResponse).toList();
        return null;
    }

    private String createNameForOrder(List<OrderLineItems> orderLineItems){
        StringBuilder orderNameBuilder = new StringBuilder();
        for (OrderLineItems oli: orderLineItems){
            orderNameBuilder.append(oli.getItemName()).append("(").append(oli.getQuantity()).append(") | ");
        }
        String orderName = orderNameBuilder.toString();
        return orderName.substring(0, orderName.length()-2);
    }

    private Order findOrder(String orderNumber){
        return orderRepository.findOrderByOrderNumber(orderNumber)
                .orElseThrow(()-> new FindException("orderNumber - такого замовлення не існує;"));
    }

    public List<OrderResponse> findOrders(Long customerId){
        return orderRepository.findOrdersByCustomerId(customerId)
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    private OrderLineItems mapToPojo (OrderLineItemsRequestDto orderLineItemsRequestDto){
        System.out.println(orderLineItemsRequestDto.getItemName());
        return OrderLineItems.builder()
                .skuCode(orderLineItemsRequestDto.getSkuCode())
                .itemName(orderLineItemsRequestDto.getItemName())
                .price(orderLineItemsRequestDto.getPrice())
                .quantity(orderLineItemsRequestDto.getQuantity())
                .build();
    }

    private OrderResponse mapToOrderResponse(Order order){
        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .orderName(order.getOrderName())
                .createdAt(order.getCreatedAt())
                .orderLineItemsResponseDto(order.getOrderLineItems()
                        .stream()
                        .map(this::mapToItemResponse)
                        .toList())
                .build();
    }

    private OrderLineItemsResponseDto mapToItemResponse(OrderLineItems items){
        return OrderLineItemsResponseDto.builder()
                .id(items.getId())
                .skuCode(items.getSkuCode())
                .itemName(items.getItemName())
                .price(items.getPrice())
                .quantity(items.getQuantity())
                .build();
    }

    private Response buildResponse(CustomerResponse customerResponse, OrderResponse orderResponse){
        return Response.builder()
                .name(customerResponse.getName())
                .surname(customerResponse.getSurname())
                .telephoneNumber(customerResponse.getTelephoneNumber())
                .order(orderResponse)
                .build();
    }
}
