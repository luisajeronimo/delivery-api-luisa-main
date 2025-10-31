package com.deliverytech.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.service.ProductFolder.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 
@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos")
public class ProductController {
    @Autowired
    private IProductService productService;
 
    @PostMapping
    @Operation(summary = "Cadastrar produto",
               description = "Cria um novo produto no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<ProductResponseDTO> cadastrar(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do produto a ser criado"
            ) ProductDTO dto) {
 
        ProductResponseDTO product = productService.cadastrarProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID",
               description = "Recupera um produto específico pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductResponseDTO> buscarPorId(
            @Parameter(description = "ID do produto")
            @PathVariable Long id) {

        ProductResponseDTO product = productService.buscarProductPorId(id);
        return ResponseEntity.ok(product);
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto",
               description = "Atualiza os dados de um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ProductResponseDTO> atualizar(
            @Parameter(description = "ID do produto")
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {

        ProductResponseDTO product = productService.atualizarProduto(id, dto);
        return ResponseEntity.ok(product);
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto",
               description = "Remove um produto do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "409", description = "Produto possui pedidos associados")
    })
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID do produto")
            @PathVariable Long id) {
 
        productService.removeProduct(id);
        return ResponseEntity.noContent().build();
    }
 
    @PatchMapping("/{id}/disponibilidade")
    @Operation(summary = "Alterar disponibilidade",
               description = "Alterna a disponibilidade do produto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Disponibilidade alterada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductResponseDTO> changeAvailability(
            @Parameter(description = "ID do produto")
            @PathVariable Long id) {

        ProductResponseDTO product = productService.changeAvailability(id);
        return ResponseEntity.ok(product);
    }
 
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar por categoria",
               description = "Lista produtos de uma categoria específica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produtos encontrados")
    })
    public ResponseEntity<List<ProductResponseDTO>> buscarPorCategoria(
            @Parameter(description = "Categoria do produto")
            @PathVariable String categoria) {

        List<ProductResponseDTO> products = productService.findProductsByCategory(categoria);
        return ResponseEntity.ok(products);
    }
 
    @GetMapping("/buscar")
    @Operation(summary = "Buscar por nome",
               description = "Busca produtos pelo nome")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    public ResponseEntity<List<ProductResponseDTO>> buscarPorNome(
            @Parameter(description = "Nome do produto")
            @RequestParam String nome) {

        List<ProductResponseDTO> products = productService.buscarProductsPorNome(nome);
        return ResponseEntity.ok(products);
    }

    }