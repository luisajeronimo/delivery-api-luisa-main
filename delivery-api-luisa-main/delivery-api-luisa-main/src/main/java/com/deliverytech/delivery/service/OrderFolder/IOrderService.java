package com.deliverytech.delivery.service.OrderFolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import com.deliverytech.delivery.dto.OrderFolder.OrderDTO;
import com.deliverytech.delivery.entity.OrderFolder.OrderStatus;
 
/**
 * Interface de serviço para operações relacionadas a pedidos (Order).
 *
 * Responsabilidades:
 * - Definir operações de alto nível usadas pelo controller para criar, consultar,
 *   listar, atualizar status e cancelar pedidos.
 * - Assinar os contratos (inputs/outputs) usando DTOs para evitar acoplamento
 *   direto entre controllers e entidades de persistência.
 *
 * Observações:
 * - Implementações desta interface devem lançar exceções customizadas em casos
 *   de erro de negócio, que serão tratadas por {@code GlobalExceptionHandler}.
 */
public interface IOrderService {

	/**
	 * Cria um novo pedido com base no DTO recebido.
	 */
	OrderDTO createOrder(OrderDTO dto);
    
	/**
	 * Busca um pedido por identificador.
	 */
	OrderDTO findOrderById(Long id);
    
	/**
	 * Lista pedidos aplicando filtros de status e intervalo de datas com paginação.
	 */
	List<OrderDTO> listOrders(OrderStatus status, LocalDateTime datainicio, LocalDateTime datafim, Pageable pageable);
    
	/**
	 * Atualiza somente o status de um pedido (transições validadas pela implementação).
	 */
	OrderDTO updateOrderStatus(Long id, OrderStatus status);
    
	/**
	 * Cancela um pedido se as regras de negócio permitirem.
	 */
	void cancelOrder(Long id);
    
	/**
	 * Retorna os pedidos de um cliente específico.
	 */
	List<OrderDTO> findOrdersByCustomer(Long id);
    
	/**
	 * Retorna os pedidos de um restaurante, opcionalmente filtrando por status.
	 */
	List<OrderDTO> findOrdersByRestaurant(Long id, OrderStatus status);
    
	/**
	 * Calcula o total de um pedido (sem persistir alterações).
	 */
	BigDecimal calculateTotalOrder(Long id);
}