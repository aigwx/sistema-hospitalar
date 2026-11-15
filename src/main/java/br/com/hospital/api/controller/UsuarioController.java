package br.com.hospital.api.controller;

import br.com.hospital.api.dto.UsuarioDTO;
import br.com.hospital.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDTO> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public UsuarioDTO salvar(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.salvar(dto);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        usuarioService.excluir(id);
    }
}