
package br.com.hospital.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescricao")
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prescricao")
    private Integer idPrescricao;

    @ManyToOne
    @JoinColumn(name = "id_atendimento", nullable = false)
    private Atendimento atendimento;

    @Column(name = "data_prescricao", nullable = false,
            insertable = false, updatable = false)
    private LocalDateTime dataPrescricao;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    public Prescricao() {}

    public Integer getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Integer idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public LocalDateTime getDataPrescricao() {
        return dataPrescricao;
    }

    public void setDataPrescricao(LocalDateTime dataPrescricao) {
        this.dataPrescricao = dataPrescricao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
