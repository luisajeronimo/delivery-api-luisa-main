package com.deliverytech.delivery.service.OrderFolder;


import com.deliverytech.delivery.dto.OrderFolder.OrderItemDTO;
import com.deliverytech.delivery.entity.OrderFolder.OrderItem;
import com.deliverytech.delivery.repository.OrderFolder.IOrderItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private IOrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrderItemDTO> getAllItemsOrder() {
        List<OrderItem> itensOrder = orderItemRepository.findAll();
        return Arrays.asList(modelMapper.map(itensOrder, OrderItemDTO[].class));
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem entity = modelMapper.map(orderItemDTO, OrderItem.class);
        OrderItem order = orderItemRepository.save(entity);
        return modelMapper.map(order, OrderItemDTO.class);
    }

    @Override
    public OrderItemDTO updateOrderItem(Long itemOrderId, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(itemOrderId).orElse(null);
        if (orderItem == null) {
            return null;
        }
        modelMapper.map(orderItemDTO, orderItem);
        orderItem = orderItemRepository.save(orderItem);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    @Override
    public OrderItemDTO getOrderItem(Long itemOrderId) {
        return orderItemRepository.findById(itemOrderId).map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class)).orElse(null);
    }

    @Override
    public void deleteOrderItem(Long itemOrderId) {
        orderItemRepository.deleteById(itemOrderId);
    }
}
