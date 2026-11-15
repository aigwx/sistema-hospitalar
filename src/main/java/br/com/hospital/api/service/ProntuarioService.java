
package br.com.hospital.api.service;

import br.com.hospital.api.dto.ProntuarioDTO;
import br.com.hospital.api.model.Paciente;
import br.com.hospital.api.model.Prontuario;
import br.com.hospital.api.repository.PacienteRepository;
import br.com.hospital.api.repository.ProntuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final PacienteRepository pacienteRepository;

    public ProntuarioService(
            ProntuarioRepository prontuarioRepository,
            PacienteRepository pacienteRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public List<ProntuarioDTO> listarTodos() {
        return prontuarioRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ProntuarioDTO buscarPorId(Integer id) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        return converterParaDTO(prontuario);
    }

    public ProntuarioDTO salvar(ProntuarioDTO dto) {

        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        if (prontuarioRepository.existsByPaciente_IdPaciente(paciente.getIdPaciente())) {
            throw new RuntimeException("Já existe um prontuário para este paciente");
        }

        Prontuario prontuario = new Prontuario();

        prontuario.setPaciente(paciente);
        prontuario.setObservacoesGerais(dto.getObservacoesGerais());

        prontuario = prontuarioRepository.save(prontuario);

        prontuarioRepository.flush();

        return converterParaDTO(prontuarioRepository.findById(prontuario.getIdProntuario())
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado")));
    }

    public ProntuarioDTO atualizar(Integer id, ProntuarioDTO dto) {

        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        prontuario.setObservacoesGerais(dto.getObservacoesGerais());

        return converterParaDTO(prontuarioRepository.save(prontuario));
    }

    private ProntuarioDTO converterParaDTO(Prontuario prontuario) {

        ProntuarioDTO dto = new ProntuarioDTO();

        dto.setIdProntuario(prontuario.getIdProntuario());
        dto.setIdPaciente(prontuario.getPaciente().getIdPaciente());
        dto.setDataCriacao(prontuario.getDataCriacao());
        dto.setObservacoesGerais(prontuario.getObservacoesGerais());

        return dto;
    }
}
