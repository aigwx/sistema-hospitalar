package br.com.hospital.api.controller;

import br.com.hospital.api.dto.EnfermeiroDTO;
import br.com.hospital.api.service.EnfermeiroService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enfermeiros")
public class EnfermeiroController {

    private final EnfermeiroService enfermeiroService;

    public EnfermeiroController(EnfermeiroService enfermeiroService) {
        this.enfermeiroService = enfermeiroService;
    }

    @GetMapping
    public List<EnfermeiroDTO> listarTodos() {
        return enfermeiroService.listarTodos();
    }

    @GetMapping("/{id}")
    public EnfermeiroDTO buscarPorId(@PathVariable Integer id) {
        return enfermeiroService.buscarPorId(id);
    }

    @PostMapping
    public EnfermeiroDTO salvar(@Valid @RequestBody EnfermeiroDTO dto) {
        return enfermeiroService.salvar(dto);
    }

    @PutMapping("/{id}")
    public EnfermeiroDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EnfermeiroDTO dto) {
        return enfermeiroService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        enfermeiroService.excluir(id);
    }
}