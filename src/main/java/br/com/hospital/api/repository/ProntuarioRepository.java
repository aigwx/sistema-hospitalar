package br.com.hospital.api.repository;

import br.com.hospital.api.model.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Integer> {

    boolean existsByPaciente_IdPaciente(Integer idPaciente);
}
