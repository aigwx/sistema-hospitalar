package br.com.hospital.api.service;

import br.com.hospital.api.dto.EnfermeiroDTO;
import br.com.hospital.api.model.Enfermeiro;
import br.com.hospital.api.model.Profissional;
import br.com.hospital.api.repository.EnfermeiroRepository;
import br.com.hospital.api.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnfermeiroService {

    private final EnfermeiroRepository enfermeiroRepository;
    private final ProfissionalRepository profissionalRepository;

    public EnfermeiroService(
            EnfermeiroRepository enfermeiroRepository,
            ProfissionalRepository profissionalRepository) {
        this.enfermeiroRepository = enfermeiroRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<EnfermeiroDTO> listarTodos() {
        return enfermeiroRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public EnfermeiroDTO buscarPorId(Integer id) {
        Enfermeiro enfermeiro = enfermeiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enfermeiro não encontrado"));

        return converterParaDTO(enfermeiro);
    }

    public EnfermeiroDTO salvar(EnfermeiroDTO dto) {
        Profissional profissional = profissionalRepository.findById(dto.getIdProfissional())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Enfermeiro enfermeiro = new Enfermeiro();
        enfermeiro.setProfissional(profissional);
        enfermeiro.setCoren(dto.getCoren());

        return converterParaDTO(enfermeiroRepository.save(enfermeiro));
    }

    public EnfermeiroDTO atualizar(Integer id, EnfermeiroDTO dto) {
        Enfermeiro enfermeiro = enfermeiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enfermeiro não encontrado"));

        enfermeiro.setCoren(dto.getCoren());

        return converterParaDTO(enfermeiroRepository.save(enfermeiro));
    }

    public void excluir(Integer id) {
        enfermeiroRepository.deleteById(id);
    }

    private EnfermeiroDTO converterParaDTO(Enfermeiro enfermeiro) {
        EnfermeiroDTO dto = new EnfermeiroDTO();

        dto.setIdProfissional(enfermeiro.getIdProfissional());
        dto.setCoren(enfermeiro.getCoren());

        return dto;
    }
}