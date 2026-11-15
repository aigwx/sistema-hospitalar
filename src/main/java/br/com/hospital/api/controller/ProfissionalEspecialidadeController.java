package br.com.hospital.api.controller;

import br.com.hospital.api.dto.AssociacaoEspecialidadeDTO;
import br.com.hospital.api.service.ProfissionalEspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/medicos")
public class ProfissionalEspecialidadeController {

    private final ProfissionalEspecialidadeService service;

    public ProfissionalEspecialidadeController(
            ProfissionalEspecialidadeService service) {
        this.service = service;
    }


    @PostMapping("/especialidades")
    public AssociacaoEspecialidadeDTO associar(
            @Valid @RequestBody AssociacaoEspecialidadeDTO dto) {
        return service.associar(dto);
    }
    @GetMapping("/{id}/especialidades")
    public List<AssociacaoEspecialidadeDTO> listarPorMedico(
            @PathVariable Integer id) {
        return service.listarPorMedico(id);
    }

    @DeleteMapping("/{idMedico}/especialidades/{idEspecialidade}")
    public void remover(
            @PathVariable Integer idMedico,
            @PathVariable Integer idEspecialidade) {
        service.remover(idMedico, idEspecialidade);
    }
}
