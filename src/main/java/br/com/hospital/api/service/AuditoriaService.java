package br.com.hospital.api.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    private final JdbcTemplate jdbcTemplate;

    public AuditoriaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void definirUsuarioLogado(Integer idUsuario) {
        jdbcTemplate.update(
                "SET @id_usuario_logado = ?",
                idUsuario
        );
    }
}