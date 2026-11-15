package br.com.hospital.api.service;

import br.com.hospital.api.dto.UsuarioDTO;
import br.com.hospital.api.model.GrupoUsuario;
import br.com.hospital.api.model.Usuario;
import br.com.hospital.api.repository.GrupoUsuarioRepository;
import br.com.hospital.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final GrupoUsuarioRepository grupoUsuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            GrupoUsuarioRepository grupoUsuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.grupoUsuarioRepository = grupoUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public UsuarioDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return converterParaDTO(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Já existe um usuário com esse e-mail");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        usuario.setGrupos(buscarGrupos(dto.getIdsGrupos()));

        return converterParaDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO atualizar(Integer id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean emailEmUso = usuarioRepository.existsByEmail(dto.getEmail());

        if (emailEmUso && !usuario.getEmail().equals(dto.getEmail())) {
            throw new RuntimeException("Já existe um usuário com esse e-mail");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));

        if (dto.getAtivo() != null) {
            usuario.setAtivo(dto.getAtivo());
        }

        usuario.setGrupos(buscarGrupos(dto.getIdsGrupos()));

        return converterParaDTO(usuarioRepository.save(usuario));
    }

    public void excluir(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setAtivo(false);

        usuarioRepository.save(usuario);
    }

    private Set<GrupoUsuario> buscarGrupos(Set<Integer> idsGrupos) {
        if (idsGrupos == null || idsGrupos.isEmpty()) {
            return new HashSet<>();
        }

        List<GrupoUsuario> grupos = grupoUsuarioRepository.findAllById(idsGrupos);

        if (grupos.size() != idsGrupos.size()) {
            throw new RuntimeException("Um ou mais grupos não foram encontrados");
        }

        return new HashSet<>(grupos);
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();

        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setAtivo(usuario.getAtivo());

        Set<Integer> idsGrupos = new HashSet<>();

        for (GrupoUsuario grupo : usuario.getGrupos()) {
            idsGrupos.add(grupo.getIdGrupo());
        }

        dto.setIdsGrupos(idsGrupos);

        return dto;
    }
}