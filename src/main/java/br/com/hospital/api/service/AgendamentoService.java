package br.com.hospital.api.service;

import br.com.hospital.api.dto.AgendamentoDTO;
import br.com.hospital.api.model.*;
import br.com.hospital.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final AuditoriaService auditoriaService;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            PacienteRepository pacienteRepository,
            ProfissionalRepository profissionalRepository,
            AuditoriaService auditoriaService) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.auditoriaService = auditoriaService;
    }

    public List<AgendamentoDTO> listarTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public AgendamentoDTO buscarPorId(Integer id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        return converterParaDTO(agendamento);
    }

    @Transactional
    public AgendamentoDTO salvar(AgendamentoDTO dto, Integer idUsuario) {

        auditoriaService.definirUsuarioLogado(idUsuario);

        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.getIdProfissional())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Agendamento agendamento = new Agendamento();

        agendamento.setPaciente(paciente);
        agendamento.setProfissional(profissional);
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setStatus(dto.getStatus());
        agendamento.setMotivo(dto.getMotivo());

        return converterParaDTO(agendamentoRepository.save(agendamento));
    }

    @Transactional
    public AgendamentoDTO atualizar(Integer id, AgendamentoDTO dto, Integer idUsuario) {

        auditoriaService.definirUsuarioLogado(idUsuario);

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.getIdProfissional())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        agendamento.setPaciente(paciente);
        agendamento.setProfissional(profissional);
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setStatus(dto.getStatus());
        agendamento.setMotivo(dto.getMotivo());

        return converterParaDTO(agendamentoRepository.save(agendamento));
    }

    private AgendamentoDTO converterParaDTO(Agendamento agendamento) {

        AgendamentoDTO dto = new AgendamentoDTO();

        dto.setIdAgendamento(agendamento.getIdAgendamento());
        dto.setIdPaciente(agendamento.getPaciente().getIdPaciente());
        dto.setIdProfissional(agendamento.getProfissional().getIdProfissional());
        dto.setDataHora(agendamento.getDataHora());
        dto.setStatus(agendamento.getStatus());
        dto.setMotivo(agendamento.getMotivo());

        return dto;
    }
}