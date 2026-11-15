
package br.com.hospital.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_prescricao")
public class ItemPrescricao {

    @EmbeddedId
    private ItemPrescricaoId id;

    @ManyToOne
    @MapsId("idPrescricao")
    @JoinColumn(name = "id_prescricao", nullable = false)
    private Prescricao prescricao;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;

    @Column(name = "dosagem", nullable = false, length = 50)
    private String dosagem;

    @Column(name = "frequencia", nullable = false, length = 50)
    private String frequencia;

    @Column(name = "duracao", nullable = false, length = 50)
    private String duracao;

    public ItemPrescricao() {}

    public ItemPrescricaoId getId() {
        return id;
    }

    public void setId(ItemPrescricaoId id) {
        this.id = id;
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
}
