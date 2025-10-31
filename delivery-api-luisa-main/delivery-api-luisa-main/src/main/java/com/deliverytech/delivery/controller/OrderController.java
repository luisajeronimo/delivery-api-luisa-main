package com.deliverytech.delivery.controller;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.deliverytech.delivery.dto.OrderFolder.*;
import com.deliverytech.delivery.entity.OrderFolder.Order;
import com.deliverytech.delivery.entity.OrderFolder.OrderStatus;
import com.deliverytech.delivery.service.OrderFolder.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private IOrderService orderService;
 
    @PostMapping
    @Operation(summary = "Create order",
               description = "Create a new order in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "404", description = "Customer or restaurant not found"),
        @ApiResponse(responseCode = "409", description = "Product unavailable")
    })
    public ResponseEntity<OrderDTO> createOrder(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Order data to be created"
            ) OrderDTO dto) {
 
        OrderDTO order = orderService.createOrder(dto);
 
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Find order by ID",
               description = "Retrieve a specific order with all details")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> findById(
            @Parameter(description = "ID do pedido")
            @PathVariable Long id) {
        OrderDTO order = orderService.findOrderById(id);
        return ResponseEntity.ok(order);
    }
 
    @GetMapping
    @Operation(summary = "List orders",
               description = "Retrieve a list of orders with optional filters and pagination")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> list(
            @Parameter(description = "Order status")
            @RequestParam(required = false) OrderStatus status,
            @Parameter(description = "Start date")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Pagination parameters")
            Pageable pageable) {

            List<OrderDTO> orders = orderService.listOrders(status, startDate, endDate, pageable);
        return ResponseEntity.ok(orders);
    }
 
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status",
               description = "Update the status of an order")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Invalid status transition")
    })
    public ResponseEntity<OrderDTO> updateStatus(
            @Parameter(description = "ID do pedido")
            @PathVariable Long id,
            @Valid @RequestBody OrderStatus status) {

        OrderDTO order = orderService.updateOrderStatus(id, status);

        return ResponseEntity.ok(order);
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel order",
               description = "Cancel an order if possible")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Order canceled successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Order cannot be canceled")
    })
    public ResponseEntity<Void> cancelOrder(
            @Parameter(description = "Order's ID")
            @PathVariable Long id) {

        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Customer's order history",
               description = "Lists all orders for a customer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "History retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<List<OrderDTO>> findByCustomer(
            @Parameter(description = "Customer's ID")
            @PathVariable Long customerId) {

        List<OrderDTO> orders = orderService.findOrdersByCustomer(customerId);

        return ResponseEntity.ok(orders);
    }
 
    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Restaurant's orders",
               description = "Lists all orders for a restaurant")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<List<OrderDTO>> findByRestaurant(
            @Parameter(description = "Restaurant's ID")
            @PathVariable Long restauranteId,
            @Parameter(description = "Order status")
            @RequestParam(required = false) OrderStatus status) {
 
        List<OrderDTO> orders =
            orderService.findOrdersByRestaurant(restauranteId, status);
 
        return ResponseEntity.ok(orders);
    }
 
    @PostMapping("/calculate")
    @Operation(summary = "Calculate order total",
               description = "Calculates the total of an order without saving it")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Total calculated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<BigDecimal> calculateTotal(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Items for calculation"
            ) Long id) {
        BigDecimal calculation = orderService.calculateTotalOrder(id);
        return ResponseEntity.ok(calculation);
    }
}
 