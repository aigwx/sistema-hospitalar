package br.com.hospital.api.service;

import br.com.hospital.api.dto.MedicoDTO;
import br.com.hospital.api.model.Medico;
import br.com.hospital.api.model.Profissional;
import br.com.hospital.api.repository.MedicoRepository;
import br.com.hospital.api.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final ProfissionalRepository profissionalRepository;

    public MedicoService(
            MedicoRepository medicoRepository,
            ProfissionalRepository profissionalRepository) {
        this.medicoRepository = medicoRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<MedicoDTO> listarTodos(){
        return medicoRepository.findAll()
                .stream().map(this::converterParaDTO).toList();
    }

    public MedicoDTO buscarPorId(Integer id){
        Medico medico = medicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        return converterParaDTO(medico);
    }


    public MedicoDTO salvar(MedicoDTO dto){
        Profissional profissional = profissionalRepository.findById(dto.getIdProfissional()).orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        Medico medico = new Medico();
        medico.setProfissional(profissional);
        medico.setCrm(dto.getCrm());

        return converterParaDTO(medicoRepository.save(medico));
    }

    public MedicoDTO atualizar(Integer id, MedicoDTO dto){
        Medico medico = medicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        medico.setCrm(dto.getCrm());

        return converterParaDTO(medicoRepository.save(medico));
    }
    public void excluir(Integer id){
       medicoRepository.deleteById(id);

    }
    private MedicoDTO converterParaDTO(Medico medico){
        MedicoDTO dto = new MedicoDTO();

        dto.setIdProfissional(medico.getIdProfissional());
        dto.setCrm(medico.getCrm());

        return dto;
    }
}
