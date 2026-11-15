
package br.com.hospital.api.service;

import br.com.hospital.api.dto.ExameDTO;
import br.com.hospital.api.model.Atendimento;
import br.com.hospital.api.model.Exame;
import br.com.hospital.api.repository.AtendimentoRepository;
import br.com.hospital.api.repository.ExameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExameService {

    private final ExameRepository exameRepository;
    private final AtendimentoRepository atendimentoRepository;

    public ExameService(
            ExameRepository exameRepository,
            AtendimentoRepository atendimentoRepository) {
        this.exameRepository = exameRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    public List<ExameDTO> listarTodos() {
        return exameRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ExameDTO buscarPorId(Integer id) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não encontrado"));

        return converterParaDTO(exame);
    }

    public ExameDTO salvar(ExameDTO dto) {
        Atendimento atendimento = atendimentoRepository.findById(dto.getIdAtendimento())
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        Exame exame = new Exame();
        exame.setAtendimento(atendimento);
        exame.setTipo(dto.getTipo());
        exame.setDataResultado(dto.getDataResultado());
        exame.setResultado(dto.getResultado());
        exame.setStatus(dto.getStatus());

        return converterParaDTO(exameRepository.save(exame));
    }

    public ExameDTO atualizar(Integer id, ExameDTO dto) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não encontrado"));

        Atendimento atendimento = atendimentoRepository.findById(dto.getIdAtendimento())
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        exame.setAtendimento(atendimento);
        exame.setTipo(dto.getTipo());
        exame.setDataResultado(dto.getDataResultado());
        exame.setResultado(dto.getResultado());
        exame.setStatus(dto.getStatus());

        return converterParaDTO(exameRepository.save(exame));
    }

    public void excluir(Integer id) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não encontrado"));

        exameRepository.delete(exame);
    }

    private ExameDTO converterParaDTO(Exame exame) {
        ExameDTO dto = new ExameDTO();

        dto.setIdExame(exame.getIdExame());
        dto.setIdAtendimento(exame.getAtendimento().getIdAtendimento());
        dto.setTipo(exame.getTipo());
        dto.setDataSolicitacao(exame.getDataSolicitacao());
        dto.setDataResultado(exame.getDataResultado());
        dto.setResultado(exame.getResultado());
        dto.setStatus(exame.getStatus());

        return dto;
    }
}
