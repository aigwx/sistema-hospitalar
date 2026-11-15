
package br.com.hospital.api.controller;

import br.com.hospital.api.dto.PrescricaoDTO;
import br.com.hospital.api.service.PrescricaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescricoes")
public class PrescricaoController {

    private final PrescricaoService prescricaoService;

    public PrescricaoController(PrescricaoService prescricaoService) {
        this.prescricaoService = prescricaoService;
    }

    @GetMapping
    public List<PrescricaoDTO> listarTodos() {
        return prescricaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public PrescricaoDTO buscarPorId(@PathVariable Integer id) {
        return prescricaoService.buscarPorId(id);
    }

    @PostMapping
    public PrescricaoDTO salvar(@Valid @RequestBody PrescricaoDTO dto) {
        return prescricaoService.salvar(dto);
    }

    @PutMapping("/{id}")
    public PrescricaoDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PrescricaoDTO dto) {
        return prescricaoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        prescricaoService.excluir(id);
    }
}
