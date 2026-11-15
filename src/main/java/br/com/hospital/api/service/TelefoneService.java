
package br.com.hospital.api.service;

import br.com.hospital.api.dto.TelefoneDTO;
import br.com.hospital.api.model.Paciente;
import br.com.hospital.api.model.Telefone;
import br.com.hospital.api.repository.PacienteRepository;
import br.com.hospital.api.repository.TelefoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final PacienteRepository pacienteRepository;

    public TelefoneService(
            TelefoneRepository telefoneRepository,
            PacienteRepository pacienteRepository) {
        this.telefoneRepository = telefoneRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public List<TelefoneDTO> listarTodos() {
        return telefoneRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public TelefoneDTO buscarPorId(Integer id) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

        return converterParaDTO(telefone);
    }

    public TelefoneDTO salvar(TelefoneDTO dto) {

        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Telefone telefone = new Telefone();
        telefone.setPaciente(paciente);
        telefone.setTelefone(dto.getTelefone());

        return converterParaDTO(telefoneRepository.save(telefone));
    }

    public TelefoneDTO atualizar(Integer id, TelefoneDTO dto) {

        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        telefone.setPaciente(paciente);
        telefone.setTelefone(dto.getTelefone());

        return converterParaDTO(telefoneRepository.save(telefone));
    }

    public void excluir(Integer id) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

        telefoneRepository.delete(telefone);
    }

    private TelefoneDTO converterParaDTO(Telefone telefone) {

        TelefoneDTO dto = new TelefoneDTO();

        dto.setIdTelefone(telefone.getIdTelefone());
        dto.setIdPaciente(telefone.getPaciente().getIdPaciente());
        dto.setTelefone(telefone.getTelefone());

        return dto;
    }
}
