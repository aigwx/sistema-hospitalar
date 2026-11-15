package br.com.hospital.api.service;

import br.com.hospital.api.dto.EspecialidadeDTO;
import br.com.hospital.api.model.Especialidade;
import br.com.hospital.api.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    public List<EspecialidadeDTO> listarTodos() {
        return especialidadeRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public EspecialidadeDTO buscarPorId(Integer id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        return converterParaDTO(especialidade);
    }

    public EspecialidadeDTO salvar(EspecialidadeDTO dto) {
        Especialidade especialidade = new Especialidade();

        especialidade.setNome(dto.getNome());
        especialidade.setDescricao(dto.getDescricao());

        return converterParaDTO(especialidadeRepository.save(especialidade));
    }

    public EspecialidadeDTO atualizar(Integer id, EspecialidadeDTO dto) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        especialidade.setNome(dto.getNome());
        especialidade.setDescricao(dto.getDescricao());

        return converterParaDTO(especialidadeRepository.save(especialidade));
    }

    public void excluir(Integer id) {
        especialidadeRepository.deleteById(id);
    }

    private EspecialidadeDTO converterParaDTO(Especialidade especialidade) {
        EspecialidadeDTO dto = new EspecialidadeDTO();

        dto.setIdEspecialidade(especialidade.getIdEspecialidade());
        dto.setNome(especialidade.getNome());
        dto.setDescricao(especialidade.getDescricao());

        return dto;
    }
}