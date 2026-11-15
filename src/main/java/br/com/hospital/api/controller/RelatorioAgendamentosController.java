package br.com.hospital.api.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioAgendamentosController {

    private final JdbcTemplate jdbcTemplate;

    public RelatorioAgendamentosController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public record AgendamentoResumo(
            Integer idAgendamento,
            String paciente,
            String profissional,
            LocalDateTime dataHora,
            String status,
            String motivo
    ) {}

    @GetMapping("/agendamentos")
    public List<AgendamentoResumo> listarAgendamentos() {
        String sql = """
                SELECT id_agendamento, paciente, profissional, data_hora, status, motivo
                FROM vw_agendamentos
                ORDER BY data_hora DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new AgendamentoResumo(
                rs.getInt("id_agendamento"),
                rs.getString("paciente"),
                rs.getString("profissional"),
                rs.getTimestamp("data_hora").toLocalDateTime(),
                rs.getString("status"),
                rs.getString("motivo")
        ));
    }
}
