package br.com.hospital.api.service;

import br.com.hospital.api.dto.PrescricaoDTO;
import br.com.hospital.api.model.Atendimento;
import br.com.hospital.api.model.Prescricao;
import br.com.hospital.api.repository.AtendimentoRepository;
import br.com.hospital.api.repository.PrescricaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescricaoService {

    private final PrescricaoRepository prescricaoRepository;
    private final AtendimentoRepository atendimentoRepository;

    public PrescricaoService(
            PrescricaoRepository prescricaoRepository,
            AtendimentoRepository atendimentoRepository) {
        this.prescricaoRepository = prescricaoRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    public List<PrescricaoDTO> listarTodos() {
        return prescricaoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public PrescricaoDTO buscarPorId(Integer id) {
        Prescricao prescricao = prescricaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada"));

        return converterParaDTO(prescricao);
    }

    public PrescricaoDTO salvar(PrescricaoDTO dto) {
        Atendimento atendimento = atendimentoRepository.findById(dto.getIdAtendimento())
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        Prescricao prescricao = new Prescricao();
        prescricao.setAtendimento(atendimento);
        prescricao.setObservacoes(dto.getObservacoes());

        prescricao = prescricaoRepository.save(prescricao);

        return converterParaDTO(prescricao);
    }

    public PrescricaoDTO atualizar(Integer id, PrescricaoDTO dto) {
        Prescricao prescricao = prescricaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada"));

        Atendimento atendimento = atendimentoRepository.findById(dto.getIdAtendimento())
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        prescricao.setAtendimento(atendimento);
        prescricao.setObservacoes(dto.getObservacoes());

        return converterParaDTO(prescricaoRepository.save(prescricao));
    }

    public void excluir(Integer id) {
        Prescricao prescricao = prescricaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada"));

        prescricaoRepository.delete(prescricao);
    }

    private PrescricaoDTO converterParaDTO(Prescricao prescricao) {
        PrescricaoDTO dto = new PrescricaoDTO();

        dto.setIdPrescricao(prescricao.getIdPrescricao());
        dto.setIdAtendimento(prescricao.getAtendimento().getIdAtendimento());
        dto.setDataPrescricao(prescricao.getDataPrescricao());
        dto.setObservacoes(prescricao.getObservacoes());

        return dto;
    }
}
