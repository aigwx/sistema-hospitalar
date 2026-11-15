
package br.com.hospital.api.controller;

import br.com.hospital.api.dto.ProntuarioDTO;
import br.com.hospital.api.service.ProntuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    private final ProntuarioService service;

    public ProntuarioController(ProntuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProntuarioDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ProntuarioDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ProntuarioDTO salvar(@Valid @RequestBody ProntuarioDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    public ProntuarioDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProntuarioDTO dto) {
        return service.atualizar(id, dto);
    }
}
