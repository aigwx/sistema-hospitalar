package br.com.hospital.api.controller;

import br.com.hospital.api.dto.EspecialidadeDTO;
import br.com.hospital.api.service.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @GetMapping
    public List<EspecialidadeDTO> listarTodos() {
        return especialidadeService.listarTodos();
    }

    @GetMapping("/{id}")
    public EspecialidadeDTO buscarPorId(@PathVariable Integer id) {
        return especialidadeService.buscarPorId(id);
    }

    @PostMapping
    public EspecialidadeDTO salvar(@Valid @RequestBody EspecialidadeDTO dto) {
        return especialidadeService.salvar(dto);
    }

    @PutMapping("/{id}")
    public EspecialidadeDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EspecialidadeDTO dto) {
        return especialidadeService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        especialidadeService.excluir(id);
    }
}