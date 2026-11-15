package br.com.hospital.api.controller;

import br.com.hospital.api.dto.GrupoUsuarioDTO;
import br.com.hospital.api.service.GrupoUsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoUsuarioController {

    private final GrupoUsuarioService grupoUsuarioService;

    public GrupoUsuarioController(GrupoUsuarioService grupoUsuarioService) {
        this.grupoUsuarioService = grupoUsuarioService;
    }

    @GetMapping
    public List<GrupoUsuarioDTO> listarTodos() {
        return grupoUsuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public GrupoUsuarioDTO buscarPorId(@PathVariable Integer id) {
        return grupoUsuarioService.buscarPorId(id);
    }

    @PostMapping
    public GrupoUsuarioDTO salvar(@Valid @RequestBody GrupoUsuarioDTO dto) {
        return grupoUsuarioService.salvar(dto);
    }

    @PutMapping("/{id}")
    public GrupoUsuarioDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody GrupoUsuarioDTO dto) {
        return grupoUsuarioService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        grupoUsuarioService.excluir(id);
    }
}