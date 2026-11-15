package br.com.hospital.api.repository;

import br.com.hospital.api.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
}
