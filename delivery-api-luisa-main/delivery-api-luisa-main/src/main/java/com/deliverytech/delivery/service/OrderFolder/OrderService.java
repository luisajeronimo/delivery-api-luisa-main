package com.deliverytech.delivery.service.OrderFolder;

import com.deliverytech.delivery.dto.OrderFolder.OrderDTO;
import com.deliverytech.delivery.dto.OrderFolder.OrderResponseDTO;
import com.deliverytech.delivery.entity.OrderFolder.Order;
import com.deliverytech.delivery.repository.OrderFolder.IOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        ModelMapper modelMapper = new ModelMapper();
        List<Order> orders = orderRepository.findAll();
        return Arrays.asList(modelMapper.map(orders, OrderResponseDTO[].class));
    }

    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Order entity = modelMapper.map(orderDTO, Order.class);
        Order order = orderRepository.save(entity);
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        modelMapper.map(orderDTO, order);
        order = orderRepository.save(order);
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO getOrder(Long orderId) {
        ModelMapper modelMapper = new ModelMapper();
        return orderRepository.findById(orderId).map(order -> modelMapper.map(order, OrderResponseDTO.class)).orElse(null);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}