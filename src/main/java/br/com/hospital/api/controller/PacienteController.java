package br.com.hospital.api.controller;

import br.com.hospital.api.dto.PacienteDTO;
import br.com.hospital.api.service.PacienteService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<PacienteDTO> listarTodos() {
        return pacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public PacienteDTO buscarPorId(@PathVariable Integer id) {
        return pacienteService.buscarPorId(id);
    }

    @PostMapping
    public PacienteDTO salvar(@Valid @RequestBody PacienteDTO paciente) {
        return pacienteService.salvar(paciente);
    }

    @PutMapping("/{id}")
    public PacienteDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PacienteDTO paciente) {

        return pacienteService.atualizar(id, paciente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        pacienteService.excluir(id);
    }
}