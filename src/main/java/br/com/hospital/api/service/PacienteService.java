package br.com.hospital.api.service;

import br.com.hospital.api.dto.PacienteDTO;
import br.com.hospital.api.model.Paciente;
import br.com.hospital.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public PacienteDTO buscarPorId(Integer id) {
     Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
     return converterParaDTO(paciente);
    }

    public PacienteDTO salvar(PacienteDTO dto) {
        Paciente paciente = converterParaEntidade(dto);

        Paciente salvo = pacienteRepository.save(paciente);

        return converterParaDTO(salvo);
    }

    public void excluir(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public PacienteDTO atualizar(Integer id, PacienteDTO dto) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setLogradouro(dto.getLogradouro());
        paciente.setNumero(dto.getNumero());
        paciente.setCidade(dto.getCidade());
        paciente.setNumeroCarteirinha(dto.getNumeroCarteirinha());

        return converterParaDTO(pacienteRepository.save(paciente));

    }

    private PacienteDTO converterParaDTO(Paciente paciente){

        PacienteDTO dto = new PacienteDTO();

        dto.setIdPaciente(paciente.getIdPaciente());
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCpf());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setLogradouro(paciente.getLogradouro());
        dto.setNumero(paciente.getNumero());
        dto.setCidade(paciente.getCidade());
        dto.setNumeroCarteirinha(paciente.getNumeroCarteirinha());

        return dto;
    }

    private Paciente converterParaEntidade(PacienteDTO dto){

        Paciente paciente = new Paciente();

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setLogradouro(dto.getLogradouro());
        paciente.setNumero(dto.getNumero());
        paciente.setCidade(dto.getCidade());
        paciente.setNumeroCarteirinha(dto.getNumeroCarteirinha());

        return paciente;
    }
}