package br.com.hospital.api.service;

import br.com.hospital.api.model.Usuario;

import br.com.hospital.api.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));

        String[] perfis = usuario.getGrupos().stream()
                .map(grupo -> grupo.getNome().toUpperCase())
                .toArray(String[]::new);

        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenhaHash())
                .disabled(!usuario.getAtivo())
                .roles(perfis)
                .build();
    }
}
