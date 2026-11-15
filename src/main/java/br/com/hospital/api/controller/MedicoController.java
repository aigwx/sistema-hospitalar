package br.com.hospital.api.controller;
import br.com.hospital.api.dto.MedicoDTO;
import br.com.hospital.api.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService){
        this.medicoService = medicoService;
    }

    @GetMapping
    public List<MedicoDTO> listarTodos(){
        return medicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public MedicoDTO buscarPorId(@PathVariable Integer id) {
        return medicoService.buscarPorId(id);
    }

    @PostMapping
    public MedicoDTO salvar(@Valid @RequestBody MedicoDTO medico) {
        return medicoService.salvar(medico);
    }
    @PutMapping("/{id}")
    public MedicoDTO atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MedicoDTO medico) {
        return medicoService.atualizar(id, medico);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id){
        medicoService.excluir(id);
    }

}
