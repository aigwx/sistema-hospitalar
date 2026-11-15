package br.com.hospital.api.controller;

import br.com.hospital.api.dto.AtendimentoDTO;
import br.com.hospital.api.service.AtendimentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private final AtendimentoService service;

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AtendimentoDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public AtendimentoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public AtendimentoDTO salvar(
            @Valid @RequestBody AtendimentoDTO dto,
            @RequestHeader("X-Usuario-Id") Integer idUsuario) {
        return service.salvar(dto, idUsuario);
    }

    @PutMapping("/{id}")
    public AtendimentoDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AtendimentoDTO dto,
            @RequestHeader("X-Usuario-Id") Integer idUsuario) {
        return service.atualizar(id, dto, idUsuario);
    }
}