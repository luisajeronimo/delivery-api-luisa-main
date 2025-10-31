package com.deliverytech.delivery.controller;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
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
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private IOrderService orderService;
 
    @PostMapping
    @Operation(summary = "Criar pedido",
               description = "Cria um novo pedido no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Customere ou restaurante não encontrado"),
        @ApiResponse(responseCode = "409", description = "Produto indisponível")
    })
    public ResponseEntity<OrderDTO> criarOrder(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do pedido a ser criado"
            ) OrderDTO dto) {
 
        OrderDTO order = orderService.createOrder(dto);
 
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID",
               description = "Recupera um pedido específico com todos os detalhes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order encontrado"),
        @ApiResponse(responseCode = "404", description = "Order não encontrado")
    })
    public ResponseEntity<OrderDTO> buscarPorId(
            @Parameter(description = "ID do pedido")
            @PathVariable Long id) {
        OrderDTO order = orderService.findOrderById(id);
        return ResponseEntity.ok(order);
    }
 
    @GetMapping
    @Operation(summary = "Listar pedidos",
               description = "Lista pedidos com filtros opcionais e paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    public ResponseEntity<List<OrderDTO>> listar(
            @Parameter(description = "Status do pedido")
            @RequestParam(required = false) OrderStatus status,
            @Parameter(description = "Data inicial")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @Parameter(description = "Data final")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
 
            List<OrderDTO> orders = orderService.listOrders(status, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(orders);
    }
 
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido",
               description = "Atualiza o status de um pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Order não encontrado"),
        @ApiResponse(responseCode = "400", description = "Transição de status inválida")
    })
    public ResponseEntity<OrderDTO> atualizarStatus(
            @Parameter(description = "ID do pedido")
            @PathVariable Long id,
            @Valid @RequestBody OrderStatus status) {

        OrderDTO order = orderService.updateOrderStatus(id, status);

        return ResponseEntity.ok(order);
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido",
               description = "Cancela um pedido se possível")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Order cancelado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Order não encontrado"),
        @ApiResponse(responseCode = "400", description = "Order não pode ser cancelado")
    })
    public ResponseEntity<Void> cancelarOrder(
            @Parameter(description = "ID do pedido")
            @PathVariable Long id) {

        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Histórico do customer",
               description = "Lista todos os pedidos de um customer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Histórico recuperado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Customer não encontrado")
    })
    public ResponseEntity<List<OrderDTO>> buscarPorCustomer(
            @Parameter(description = "ID do customer")
            @PathVariable Long customerId) {

        List<OrderDTO> orders = orderService.findOrdersByCustomer(customerId);

        return ResponseEntity.ok(orders);
    }
 
    @GetMapping("/restaurante/{restauranteId}")
    @Operation(summary = "Orders do restaurante",
               description = "Lista todos os pedidos de um restaurante")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orders recuperados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<List<OrderDTO>> buscarPorRestaurante(
            @Parameter(description = "ID do restaurante")
            @PathVariable Long restauranteId,
            @Parameter(description = "Status do pedido")
            @RequestParam(required = false) OrderStatus status) {
 
        List<OrderDTO> orders =
            orderService.findOrdersByRestaurant(restauranteId, status);
 
        return ResponseEntity.ok(orders);
    }
 
    @PostMapping("/calcular")
    @Operation(summary = "Calcular total do pedido",
               description = "Calcula o total de um pedido sem salvá-lo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Total calculado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<BigDecimal> calcularTotal(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Itens para cálculo"
            ) Long id) {
        BigDecimal calculo = orderService.calculateTotalOrder(id);
        return ResponseEntity.ok(calculo);
    }
}
 