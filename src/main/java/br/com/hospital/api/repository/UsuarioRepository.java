
package br.com.hospital.api.repository;

import br.com.hospital.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"grupos"})
    Optional<Usuario> findByEmail(String email);
}
