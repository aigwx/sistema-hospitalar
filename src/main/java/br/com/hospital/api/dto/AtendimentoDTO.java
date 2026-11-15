
package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AtendimentoDTO {

    private Integer idAtendimento;

    @NotNull(message = "O agendamento é obrigatório")
    private Integer idAgendamento;

    @NotNull(message = "A data e hora são obrigatórias")
    private LocalDateTime dataHora;

    private String observacoes;

    private String diagnostico;

    public Integer getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Integer idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public Integer getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Integer idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
}
