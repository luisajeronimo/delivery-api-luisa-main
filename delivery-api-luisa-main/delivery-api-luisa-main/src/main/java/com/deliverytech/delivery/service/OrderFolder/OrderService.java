package com.deliverytech.delivery.service.OrderFolder;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import lombok.Data;
 
import com.deliverytech.delivery.dto.OrderFolder.*;
import com.deliverytech.delivery.dto.ProductFolder.*;
import com.deliverytech.delivery.entity.ProductFolder.*;
import com.deliverytech.delivery.entity.OrderFolder.*;
import com.deliverytech.delivery.entity.CustomerFolder.*;
import com.deliverytech.delivery.entity.RestaurantFolder.*;

import com.deliverytech.delivery.config.exception.BusinessException;
import com.deliverytech.delivery.config.exception.EntityNotFoundException;
import com.deliverytech.delivery.repository.CustomerFolder.ICustomerRepository;
import com.deliverytech.delivery.repository.OrderFolder.IOrderRepository;
import com.deliverytech.delivery.repository.ProductFolder.IProductRepository;
import com.deliverytech.delivery.repository.RestaurantFolder.IRestaurantRepository;

public class OrderService implements IOrderService {

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IRestaurantRepository restaurantRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;
 
    @Override
    public OrderDTO createOrder(OrderDTO dto) {

        // 1. Validar cliente existe e está ativo
        Customer customer = customerRepository.findById(dto.getCustomerId())
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if (!customer.isActive()) {
            throw new BusinessException("Inactive customers cannot place orders");
        }

        // 2. Validar restaurante existe e está ativo
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
            .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        if (!restaurant.isActive()) {
            throw new BusinessException("Restaurant is not available");
        }

        // 3. Validar todos os produtos existem e estão disponíveis

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        //Customer c = customerRepository.findById(dto.getCustomerId());
        // Implementaion from CrudRepository 
        /**
         * Retrieves an entity by its id.
         *
         * @param id must not be {@literal null}.
         * @return the entity with the given id or {@literal Optional#empty()} if none found.
         * @throws IllegalArgumentException if {@literal id} is {@literal null}.
         */

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + itemDTO.getProductId()));
            if (!product.getStatus().equals(ProductStatus.AVAILABLE)) {
                throw new BusinessException("Product is unavailable: " + product.getName());
            }
            if (!product.getRestaurant().getId().equals(dto.getRestaurantId())) {
                throw new BusinessException("Product does not belong to the selected restaurant");
            }

            // Criar item do pedido
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            //REVISAR ESSA LÓGICA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            orderItems.add(item);
            subtotal = subtotal.add(item.getSubtotal());
        }
 
        // 4. Calcular total do pedido
        BigDecimal deliveryFee = restaurant.getDeliveryFee();
        BigDecimal totalValue = subtotal.add(deliveryFee);

        // 5. Salvar pedido
        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setSubtotal(subtotal);
        order.setDeliveryFee(deliveryFee);
        order.setTotal(totalValue);

        Order orderSaved = orderRepository.save(order);
 
        // 6. Salvar itens do pedido
        for (OrderItem item : orderItems) {
            item.setOrder(orderSaved);
        }
        orderSaved.setItems(orderItems);

        // 8. Retornar order criado
        return mapper.map(orderSaved, OrderDTO.class);
    }
 
    @Override
    public OrderDTO findOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id  + " not found"));
        return mapper.map(order, OrderDTO.class);
    }
 
    @Override
    public List<OrderDTO> listOrders(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        List<Order> orders = orderRepository.findByStatusAndOrderDateBetweenOrderByOrderDateDesc(status, startDate, endDate, pageable);
        return Arrays.asList(mapper.map(orders, OrderDTO[].class));
    }

    // Implementar lógica de transições válidas
    private boolean isValidTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        switch (currentStatus) {
            case PENDING:
                return newStatus == OrderStatus.CONFIRMED || newStatus == OrderStatus.CANCELLED;
            case CONFIRMED:
                return newStatus == OrderStatus.IN_PROGRESS || newStatus == OrderStatus.CANCELLED;
            case IN_PROGRESS:
                return newStatus == OrderStatus.OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY:
                return newStatus == OrderStatus.DELIVERED;
            default:
                return false;
        }
    }
 
    private boolean canBeCancelled(OrderStatus status) {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }
 
    @Override
    public OrderDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        order.setStatus(status);
        
        // Validar transições de status permitidas
        if (!isValidTransition(order.getStatus(), status)) {
            throw new BusinessException("Invalid status transition: " +
                order.getStatus() + " -> " + status);
        }
        Order orderUpdated = orderRepository.save(order);
        return mapper.map(orderUpdated, OrderDTO.class);
    }
 
    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        if (!canBeCancelled(order.getStatus())) {
            throw new BusinessException("Order cannot be cancelled in status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
 
    @Override
    public List<OrderDTO> findOrdersByCustomer(Long id) {
        List<Order> orders = orderRepository.findByCustomerIdOrderByOrderDateDesc(id);
        return Arrays.asList(mapper.map(orders, OrderDTO[].class));
    }
 
    @Override
    public List<OrderDTO> findOrdersByRestaurant(Long id, OrderStatus status) {
         List<Order> orders = orderRepository.findByRestaurantIdAndStatusOrderByOrderDateDesc(id, status);
        return Arrays.asList(mapper.map(orders, OrderDTO[].class));
    }
 
    @Override
    public BigDecimal calculateTotalOrder(Long id) {
        BigDecimal total = BigDecimal.ZERO;
 
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
       
        if (order != null) {
          for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();

            BigDecimal subtotalItem = product.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotalItem);
          }
        }
        return total;
    }
 
}
 
 