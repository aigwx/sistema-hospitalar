package br.com.hospital.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;


public class AgendamentoDTO {

    private Integer idAgendamento;

    @NotNull(message = "O paciente é obrigatório")
    private Integer idPaciente;

    @NotNull(message = "O profissional é obrigatório")
    private Integer idProfissional;


    @NotNull(message = "A data e hora são obrigatórias")
    @FutureOrPresent(message = "O argumento não pode estar no passado")
    private LocalDateTime dataHora;

    @NotBlank(message = "O status é obrigatório")
    private String status;

    private String motivo;

    public Integer getIdAgendamento(){
        return idAgendamento;
    }

    public void setIdAgendamento(Integer idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
