package br.com.hospital.api.controller;

import br.com.hospital.api.dto.AgendamentoDTO;
import br.com.hospital.api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AgendamentoDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public AgendamentoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public AgendamentoDTO salvar(
            @Valid @RequestBody AgendamentoDTO dto,
            @RequestHeader("X-Usuario-Id") Integer idUsuario) {
        return service.salvar(dto, idUsuario);
    }

    @PutMapping("/{id}")
    public AgendamentoDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AgendamentoDTO dto,
            @RequestHeader("X-Usuario-Id") Integer idUsuario) {
        return service.atualizar(id, dto, idUsuario);
    }
}