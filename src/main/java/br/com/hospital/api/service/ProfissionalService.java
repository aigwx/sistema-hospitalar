package br.com.hospital.api.service;

import br.com.hospital.api.dto.ProfissionalDTO;
import br.com.hospital.api.model.Profissional;
import br.com.hospital.api.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public List<ProfissionalDTO> listarTodos() {
        return profissionalRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ProfissionalDTO buscarPorId(Integer id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        return converterParaDTO(profissional);
    }

    public ProfissionalDTO salvar(ProfissionalDTO dto) {
        Profissional profissional = converterParaEntidade(dto);

        Profissional salvo = profissionalRepository.save(profissional);

        return converterParaDTO(salvo);
    }

    public ProfissionalDTO atualizar(Integer id, ProfissionalDTO dto) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        profissional.setNome(dto.getNome());
        profissional.setCpf(dto.getCpf());
        profissional.setDataAdmissao(dto.getDataAdmissao());

        Profissional atualizado = profissionalRepository.save(profissional);

        return converterParaDTO(atualizado);
    }

    public void excluir(Integer id) {
        profissionalRepository.deleteById(id);
    }

    private ProfissionalDTO converterParaDTO(Profissional profissional) {
        ProfissionalDTO dto = new ProfissionalDTO();

        dto.setIdProfissional(profissional.getIdProfissional());
        dto.setNome(profissional.getNome());
        dto.setCpf(profissional.getCpf());
        dto.setDataAdmissao(profissional.getDataAdmissao());

        return dto;
    }

    private Profissional converterParaEntidade(ProfissionalDTO dto) {
        Profissional profissional = new Profissional();

        profissional.setNome(dto.getNome());
        profissional.setCpf(dto.getCpf());
        profissional.setDataAdmissao(dto.getDataAdmissao());

        return profissional;
    }
}