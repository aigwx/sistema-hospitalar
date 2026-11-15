package br.com.hospital.api.service;

import br.com.hospital.api.dto.AtendimentoDTO;
import br.com.hospital.api.model.Atendimento;
import br.com.hospital.api.model.Agendamento;
import br.com.hospital.api.repository.AtendimentoRepository;
import br.com.hospital.api.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AuditoriaService auditoriaService;

    public AtendimentoService(
            AtendimentoRepository atendimentoRepository,
            AgendamentoRepository agendamentoRepository,
            AuditoriaService auditoriaService) {
        this.atendimentoRepository = atendimentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.auditoriaService = auditoriaService;
    }

    public List<AtendimentoDTO> listarTodos() {
        return atendimentoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public AtendimentoDTO buscarPorId(Integer id) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        return converterParaDTO(atendimento);
    }

    @Transactional
    public AtendimentoDTO salvar(AtendimentoDTO dto, Integer idUsuario) {

        auditoriaService.definirUsuarioLogado(idUsuario);

        Agendamento agendamento = agendamentoRepository.findById(dto.getIdAgendamento())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        if (atendimentoRepository.existsByAgendamento_IdAgendamento(dto.getIdAgendamento())) {
            throw new RuntimeException("Já existe um atendimento para este agendamento");
        }

        Atendimento atendimento = new Atendimento();

        atendimento.setAgendamento(agendamento);
        atendimento.setDataHora(dto.getDataHora());
        atendimento.setObservacoes(dto.getObservacoes());
        atendimento.setDiagnostico(dto.getDiagnostico());

        return converterParaDTO(atendimentoRepository.save(atendimento));
    }

    @Transactional
    public AtendimentoDTO atualizar(Integer id, AtendimentoDTO dto, Integer idUsuario) {

        auditoriaService.definirUsuarioLogado(idUsuario);

        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        atendimento.setDataHora(dto.getDataHora());
        atendimento.setObservacoes(dto.getObservacoes());
        atendimento.setDiagnostico(dto.getDiagnostico());

        return converterParaDTO(atendimentoRepository.save(atendimento));
    }

    private AtendimentoDTO converterParaDTO(Atendimento atendimento) {

        AtendimentoDTO dto = new AtendimentoDTO();

        dto.setIdAtendimento(atendimento.getIdAtendimento());
        dto.setIdAgendamento(atendimento.getAgendamento().getIdAgendamento());
        dto.setDataHora(atendimento.getDataHora());
        dto.setObservacoes(atendimento.getObservacoes());
        dto.setDiagnostico(atendimento.getDiagnostico());

        return dto;
    }
}