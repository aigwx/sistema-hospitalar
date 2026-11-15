package br.com.hospital.api.controller;

import br.com.hospital.api.dto.ProfissionalDTO;
import br.com.hospital.api.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public List<ProfissionalDTO> listarTodos() {
        return profissionalService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProfissionalDTO buscarPorId(@PathVariable Integer id) {
        return profissionalService.buscarPorId(id);
    }

    @PostMapping
    public ProfissionalDTO salvar(@Valid @RequestBody ProfissionalDTO profissional) {
        return profissionalService.salvar(profissional);
    }

    @PutMapping("/{id}")
    public ProfissionalDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProfissionalDTO profissional) {
        return profissionalService.atualizar(id, profissional);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        profissionalService.excluir(id);
    }
}