
package br.com.hospital.api.repository;

import br.com.hospital.api.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<Exame, Integer> {
}
