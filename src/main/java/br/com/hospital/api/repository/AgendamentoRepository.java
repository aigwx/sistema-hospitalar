package br.com.hospital.api.repository;
import br.com.hospital.api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento,Integer>{
}
