
package br.com.hospital.api.service;

import br.com.hospital.api.dto.ItemPrescricaoDTO;
import br.com.hospital.api.model.*;
import br.com.hospital.api.repository.ItemPrescricaoRepository;
import br.com.hospital.api.repository.MedicamentoRepository;
import br.com.hospital.api.repository.PrescricaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPrescricaoService {

    private final ItemPrescricaoRepository itemRepository;
    private final PrescricaoRepository prescricaoRepository;
    private final MedicamentoRepository medicamentoRepository;

    public ItemPrescricaoService(
            ItemPrescricaoRepository itemRepository,
            PrescricaoRepository prescricaoRepository,
            MedicamentoRepository medicamentoRepository) {
        this.itemRepository = itemRepository;
        this.prescricaoRepository = prescricaoRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<ItemPrescricaoDTO> listarTodos() {
        return itemRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public List<ItemPrescricaoDTO> listarPorPrescricao(Integer idPrescricao) {
        return itemRepository.findByPrescricao_IdPrescricao(idPrescricao)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ItemPrescricaoDTO buscarPorId(Integer idPrescricao, Integer numeroItem) {
        ItemPrescricaoId id = new ItemPrescricaoId(idPrescricao, numeroItem);

        ItemPrescricao item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da prescrição não encontrado"));

        return converterParaDTO(item);
    }

    public ItemPrescricaoDTO salvar(ItemPrescricaoDTO dto) {
        Prescricao prescricao = prescricaoRepository.findById(dto.getIdPrescricao())
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada"));

        Medicamento medicamento = medicamentoRepository.findById(dto.getIdMedicamento())
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        ItemPrescricao item = new ItemPrescricao();

        item.setId(new ItemPrescricaoId(dto.getIdPrescricao(), dto.getNumeroItem()));
        item.setPrescricao(prescricao);
        item.setMedicamento(medicamento);
        item.setDosagem(dto.getDosagem());
        item.setFrequencia(dto.getFrequencia());
        item.setDuracao(dto.getDuracao());

        return converterParaDTO(itemRepository.save(item));
    }

    public ItemPrescricaoDTO atualizar(
            Integer idPrescricao, Integer numeroItem, ItemPrescricaoDTO dto) {

        ItemPrescricaoId id = new ItemPrescricaoId(idPrescricao, numeroItem);

        ItemPrescricao item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da prescrição não encontrado"));

        Medicamento medicamento = medicamentoRepository.findById(dto.getIdMedicamento())
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        item.setMedicamento(medicamento);
        item.setDosagem(dto.getDosagem());
        item.setFrequencia(dto.getFrequencia());
        item.setDuracao(dto.getDuracao());

        return converterParaDTO(itemRepository.save(item));
    }

    public void excluir(Integer idPrescricao, Integer numeroItem) {
        ItemPrescricaoId id = new ItemPrescricaoId(idPrescricao, numeroItem);

        ItemPrescricao item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da prescrição não encontrado"));

        itemRepository.delete(item);
    }

    private ItemPrescricaoDTO converterParaDTO(ItemPrescricao item) {
        ItemPrescricaoDTO dto = new ItemPrescricaoDTO();

        dto.setIdPrescricao(item.getPrescricao().getIdPrescricao());
        dto.setNumeroItem(item.getId().getNumeroItem());
        dto.setIdMedicamento(item.getMedicamento().getIdMedicamento());
        dto.setDosagem(item.getDosagem());
        dto.setFrequencia(item.getFrequencia());
        dto.setDuracao(item.getDuracao());

        return dto;
    }
}
