package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.config.security.TokenService;
import com.deliverytech.delivery.dto.UserFolder.AuthenticationDTO;
import com.deliverytech.delivery.dto.UserFolder.LoginResponseDTO;
import com.deliverytech.delivery.dto.UserFolder.RegisterDTO;
import com.deliverytech.delivery.entity.UserFolder.User;
import com.deliverytech.delivery.repository.UserFolder.IUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
/**
 * AuthenticationController
 *
 * Responsabilidades:
 * - Fornecer endpoints para login e registro de usuários.
 * - Autenticar credenciais via AuthenticationManager e gerar JWT via TokenService.
 *
 * Fluxo (login):
 * 1. Recebe {@link com.deliverytech.delivery.dto.UserFolder.AuthenticationDTO} no corpo da requisição.
 * 2. Cria um {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken} e delega
 *    a autenticação ao {@code AuthenticationManager} configurado.
 * 3. Em caso de sucesso, recupera o principal (usuário), gera um token JWT com {@code TokenService}
 *    e retorna um {@link com.deliverytech.delivery.dto.UserFolder.LoginResponseDTO} com o token.
 *
 * Fluxo (register):
 * 1. Recebe {@link com.deliverytech.delivery.dto.UserFolder.RegisterDTO} no corpo da requisição.
 * 2. Verifica se o e-mail já está registrado; se sim, responde 400 Bad Request.
 * 3. Criptografa a senha com BCrypt e persiste um novo {@link com.deliverytech.delivery.entity.UserFolder.User}.
 * 4. Retorna 200 OK em caso de sucesso (poderia retornar 201 CREATED com localização do recurso).
 */
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository repository;
    
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    /**
     * POST /auth/login
     * Autentica um usuário e retorna um token JWT se as credenciais estiverem corretas.
     *
     * @param data AuthenticationDTO contendo email e password
     * @return 200 OK com {@link com.deliverytech.delivery.dto.UserFolder.LoginResponseDTO} contendo o token
     */
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    /**
     * POST /auth/register
     * Registra um novo usuário (sem confirmação por e-mail neste fluxo).
     *
     * @param data RegisterDTO com informações do novo usuário
     * @return 200 OK em sucesso ou 400 Bad Request se o e-mail já existir
     */
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, data.name(),  data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}