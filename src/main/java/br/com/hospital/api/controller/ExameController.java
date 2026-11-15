
package br.com.hospital.api.controller;

import br.com.hospital.api.dto.ExameDTO;
import br.com.hospital.api.service.ExameService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
public class ExameController {

    private final ExameService exameService;

    public ExameController(ExameService exameService) {
        this.exameService = exameService;
    }

    @GetMapping
    public List<ExameDTO> listarTodos() {
        return exameService.listarTodos();
    }

    @GetMapping("/{id}")
    public ExameDTO buscarPorId(@PathVariable Integer id) {
        return exameService.buscarPorId(id);
    }

    @PostMapping
    public ExameDTO salvar(@Valid @RequestBody ExameDTO dto) {
        return exameService.salvar(dto);
    }

    @PutMapping("/{id}")
    public ExameDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ExameDTO dto) {
        return exameService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        exameService.excluir(id);
    }
}
