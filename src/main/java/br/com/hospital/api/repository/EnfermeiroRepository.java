package br.com.hospital.api.repository;

import br.com.hospital.api.model.Enfermeiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, Integer> {
}