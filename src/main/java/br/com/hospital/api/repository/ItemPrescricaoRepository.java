package br.com.hospital.api.repository;

import br.com.hospital.api.model.ItemPrescricao;
import br.com.hospital.api.model.ItemPrescricaoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPrescricaoRepository extends JpaRepository<ItemPrescricao, ItemPrescricaoId> {

    List<ItemPrescricao> findByPrescricao_IdPrescricao(Integer idPrescricao);
}
