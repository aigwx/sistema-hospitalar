
package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ItemPrescricaoDTO {

    @NotNull(message = "A prescrição é obrigatória")
    private Integer idPrescricao;

    @NotNull(message = "O número do item é obrigatório")
    private Integer numeroItem;

    @NotNull(message = "O medicamento é obrigatório")
    private Integer idMedicamento;

    @NotBlank(message = "A dosagem é obrigatória")
    @Size(max = 50, message = "A dosagem deve ter no máximo 50 caracteres")
    private String dosagem;

    @NotBlank(message = "A frequência é obrigatória")
    @Size(max = 50, message = "A frequência deve ter no máximo 50 caracteres")
    private String frequencia;

    @NotBlank(message = "A duração é obrigatória")
    @Size(max = 50, message = "A duração deve ter no máximo 50 caracteres")
    private String duracao;

    public Integer getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Integer idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
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
