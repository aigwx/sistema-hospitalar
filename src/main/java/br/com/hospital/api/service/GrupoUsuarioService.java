package br.com.hospital.api.service;

import br.com.hospital.api.dto.GrupoUsuarioDTO;
import br.com.hospital.api.model.GrupoUsuario;
import br.com.hospital.api.repository.GrupoUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoUsuarioService {

    private final GrupoUsuarioRepository grupoUsuarioRepository;

    public GrupoUsuarioService(GrupoUsuarioRepository grupoUsuarioRepository) {
        this.grupoUsuarioRepository = grupoUsuarioRepository;
    }

    public List<GrupoUsuarioDTO> listarTodos() {
        return grupoUsuarioRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public GrupoUsuarioDTO buscarPorId(Integer id) {
        GrupoUsuario grupo = grupoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        return converterParaDTO(grupo);
    }

    public GrupoUsuarioDTO salvar(GrupoUsuarioDTO dto) {
        if (grupoUsuarioRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Já existe um grupo com esse nome");
        }

        GrupoUsuario grupo = new GrupoUsuario();
        grupo.setNome(dto.getNome());
        grupo.setDescricao(dto.getDescricao());

        return converterParaDTO(grupoUsuarioRepository.save(grupo));
    }

    public GrupoUsuarioDTO atualizar(Integer id, GrupoUsuarioDTO dto) {
        GrupoUsuario grupo = grupoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        boolean nomeEmUso = grupoUsuarioRepository.existsByNome(dto.getNome());

        if (nomeEmUso && !grupo.getNome().equals(dto.getNome())) {
            throw new RuntimeException("Já existe um grupo com esse nome");
        }

        grupo.setNome(dto.getNome());
        grupo.setDescricao(dto.getDescricao());

        return converterParaDTO(grupoUsuarioRepository.save(grupo));
    }

    public void excluir(Integer id) {
        GrupoUsuario grupo = grupoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        grupoUsuarioRepository.delete(grupo);
    }

    private GrupoUsuarioDTO converterParaDTO(GrupoUsuario grupo) {
        GrupoUsuarioDTO dto = new GrupoUsuarioDTO();

        dto.setIdGrupo(grupo.getIdGrupo());
        dto.setNome(grupo.getNome());
        dto.setDescricao(grupo.getDescricao());

        return dto;
    }
}