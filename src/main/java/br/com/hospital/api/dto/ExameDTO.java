
package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ExameDTO {

    private Integer idExame;

    @NotNull(message = "O atendimento é obrigatório")
    private Integer idAtendimento;

    @NotBlank(message = "O tipo do exame é obrigatório")
    @Size(max = 100, message = "O tipo deve ter no máximo 100 caracteres")
    private String tipo;

    private LocalDateTime dataSolicitacao;

    private LocalDateTime dataResultado;

    private String resultado;

    @NotBlank(message = "O status do exame é obrigatório")
    @Size(max = 30, message = "O status deve ter no máximo 30 caracteres")
    private String status;

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public Integer getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Integer idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataResultado() {
        return dataResultado;
    }

    public void setDataResultado(LocalDateTime dataResultado) {
        this.dataResultado = dataResultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
