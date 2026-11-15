
package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class PrescricaoDTO {

    private Integer idPrescricao;

    @NotNull(message = "O atendimento é obrigatório")
    private Integer idAtendimento;

    private LocalDateTime dataPrescricao;

    @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres")
    private String observacoes;

    public Integer getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Integer idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public Integer getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Integer idAtendimento) {
        this.idAtendimento = idAtendimento;
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
