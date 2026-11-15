package br.com.hospital.api.repository;

import br.com.hospital.api.model.GrupoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Integer> {
    boolean existsByNome(String nome);
}