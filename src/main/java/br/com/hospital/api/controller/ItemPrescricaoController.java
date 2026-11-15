
package br.com.hospital.api.controller;

import br.com.hospital.api.dto.ItemPrescricaoDTO;
import br.com.hospital.api.service.ItemPrescricaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-prescricao")
public class ItemPrescricaoController {

    private final ItemPrescricaoService itemService;

    public ItemPrescricaoController(ItemPrescricaoService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemPrescricaoDTO> listarTodos() {
        return itemService.listarTodos();
    }

    @GetMapping("/prescricao/{idPrescricao}")
    public List<ItemPrescricaoDTO> listarPorPrescricao(
            @PathVariable Integer idPrescricao) {
        return itemService.listarPorPrescricao(idPrescricao);
    }

    @GetMapping("/{idPrescricao}/{numeroItem}")
    public ItemPrescricaoDTO buscarPorId(
            @PathVariable Integer idPrescricao,
            @PathVariable Integer numeroItem) {
        return itemService.buscarPorId(idPrescricao, numeroItem);
    }

    @PostMapping
    public ItemPrescricaoDTO salvar(@Valid @RequestBody ItemPrescricaoDTO dto) {
        return itemService.salvar(dto);
    }

    @PutMapping("/{idPrescricao}/{numeroItem}")
    public ItemPrescricaoDTO atualizar(
            @PathVariable Integer idPrescricao,
            @PathVariable Integer numeroItem,
            @Valid @RequestBody ItemPrescricaoDTO dto) {
        return itemService.atualizar(idPrescricao, numeroItem, dto);
    }

    @DeleteMapping("/{idPrescricao}/{numeroItem}")
    public void excluir(
            @PathVariable Integer idPrescricao,
            @PathVariable Integer numeroItem) {
        itemService.excluir(idPrescricao, numeroItem);
    }
}
