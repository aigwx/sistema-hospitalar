package br.com.hospital.api.repository;

import br.com.hospital.api.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    boolean existsByAgendamento_IdAgendamento(Integer idAgendamento);
}
