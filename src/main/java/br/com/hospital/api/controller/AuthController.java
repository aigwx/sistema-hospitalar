
package br.com.hospital.api.controller;

import br.com.hospital.api.model.Usuario;
import br.com.hospital.api.repository.UsuarioRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/me")
    public Map<String, Object> usuarioAutenticado(
            Authentication authentication) {

        Usuario usuario = usuarioRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        List<String> perfis = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .toList();

        return Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "nome", usuario.getNome(),
                "email", usuario.getEmail(),
                "perfis", perfis
        );
    }
}
