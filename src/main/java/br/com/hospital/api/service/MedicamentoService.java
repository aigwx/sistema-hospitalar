
package br.com.hospital.api.service;

import br.com.hospital.api.dto.MedicamentoDTO;
import br.com.hospital.api.model.Medicamento;
import br.com.hospital.api.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<MedicamentoDTO> listarTodos() {
        return medicamentoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public MedicamentoDTO buscarPorId(Integer id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        return converterParaDTO(medicamento);
    }

    public MedicamentoDTO salvar(MedicamentoDTO dto) {

        Medicamento medicamento = new Medicamento();

        medicamento.setNome(dto.getNome());
        medicamento.setPrincipioAtivo(dto.getPrincipioAtivo());
        medicamento.setFabricante(dto.getFabricante());

        return converterParaDTO(medicamentoRepository.save(medicamento));
    }

    public MedicamentoDTO atualizar(Integer id, MedicamentoDTO dto) {

        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        medicamento.setNome(dto.getNome());
        medicamento.setPrincipioAtivo(dto.getPrincipioAtivo());
        medicamento.setFabricante(dto.getFabricante());

        return converterParaDTO(medicamentoRepository.save(medicamento));
    }

    public void excluir(Integer id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        medicamentoRepository.delete(medicamento);
    }

    private MedicamentoDTO converterParaDTO(Medicamento medicamento) {

        MedicamentoDTO dto = new MedicamentoDTO();

        dto.setIdMedicamento(medicamento.getIdMedicamento());
        dto.setNome(medicamento.getNome());
        dto.setPrincipioAtivo(medicamento.getPrincipioAtivo());
        dto.setFabricante(medicamento.getFabricante());

        return dto;
    }
}
