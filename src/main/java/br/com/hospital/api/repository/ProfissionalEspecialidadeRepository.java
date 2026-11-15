package br.com.hospital.api.repository;

import br.com.hospital.api.model.ProfissionalEspecialidade;
import br.com.hospital.api.model.ProfissionalEspecialidadeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalEspecialidadeRepository
        extends JpaRepository<ProfissionalEspecialidade, ProfissionalEspecialidadeId> {
}