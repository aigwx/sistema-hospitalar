
package br.com.hospital.api.controller;

import br.com.hospital.api.dto.MedicamentoDTO;
import br.com.hospital.api.service.MedicamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public List<MedicamentoDTO> listarTodos() {
        return medicamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public MedicamentoDTO buscarPorId(@PathVariable Integer id) {
        return medicamentoService.buscarPorId(id);
    }

    @PostMapping
    public MedicamentoDTO salvar(@Valid @RequestBody MedicamentoDTO dto) {
        return medicamentoService.salvar(dto);
    }

    @PutMapping("/{id}")
    public MedicamentoDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MedicamentoDTO dto) {
        return medicamentoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        medicamentoService.excluir(id);
    }
}
