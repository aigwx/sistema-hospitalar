package br.com.hospital.api.repository;

import br.com.hospital.api.model.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Integer> {
}
