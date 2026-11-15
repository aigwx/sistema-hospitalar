
package br.com.hospital.api.controller;

import br.com.hospital.api.dto.TelefoneDTO;
import br.com.hospital.api.service.TelefoneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefones")
public class TelefoneController {

    private final TelefoneService telefoneService;

    public TelefoneController(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    @GetMapping
    public List<TelefoneDTO> listarTodos() {
        return telefoneService.listarTodos();
    }

    @GetMapping("/{id}")
    public TelefoneDTO buscarPorId(@PathVariable Integer id) {
        return telefoneService.buscarPorId(id);
    }

    @PostMapping
    public TelefoneDTO salvar(@Valid @RequestBody TelefoneDTO dto) {
        return telefoneService.salvar(dto);
    }

    @PutMapping("/{id}")
    public TelefoneDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody TelefoneDTO dto) {
        return telefoneService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        telefoneService.excluir(id);
    }
}
