package br.com.hospital.api.service;

import br.com.hospital.api.dto.AssociacaoEspecialidadeDTO;
import br.com.hospital.api.model.*;
import br.com.hospital.api.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProfissionalEspecialidadeService {

    private final ProfissionalEspecialidadeRepository associacaoRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public ProfissionalEspecialidadeService(
            ProfissionalEspecialidadeRepository associacaoRepository,
            MedicoRepository medicoRepository,
            EspecialidadeRepository especialidadeRepository) {
        this.associacaoRepository = associacaoRepository;
        this.medicoRepository = medicoRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    public AssociacaoEspecialidadeDTO associar(AssociacaoEspecialidadeDTO dto){

        Medico medico = medicoRepository.findById(dto.getIdProfissional()).orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        Especialidade especialidade = especialidadeRepository.findById(dto.getIdEspecialidade()).orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        ProfissionalEspecialidadeId id = new ProfissionalEspecialidadeId(
                dto.getIdProfissional(),
                dto.getIdEspecialidade()
        );

        ProfissionalEspecialidade associacao = new ProfissionalEspecialidade();
        associacao.setId(id);
        associacao.setMedico(medico);
        associacao.setEspecialidade(especialidade);

        associacaoRepository.save(associacao);

        return dto;
    }

    public List<AssociacaoEspecialidadeDTO> listarPorMedico(Integer idMedico){

        return associacaoRepository.findAll()
                .stream()
                .filter(a -> a.getMedico().getIdProfissional().equals(idMedico))
                .map(a ->{
                    AssociacaoEspecialidadeDTO dto = new AssociacaoEspecialidadeDTO();
                    dto.setIdProfissional(a.getMedico().getIdProfissional());
                    dto.setIdEspecialidade(a.getEspecialidade().getIdEspecialidade());
                    return dto;
                        })
                .toList();
    }

    public void remover(Integer idMedico, Integer idEspecialidade){

        ProfissionalEspecialidadeId id = new ProfissionalEspecialidadeId(idMedico, idEspecialidade);

        associacaoRepository.deleteById(id);
    }
}
