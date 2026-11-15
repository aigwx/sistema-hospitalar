package br.com.hospital.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "agendamento")
public class Agendamento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Integer idAgendamento;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_profissional", nullable = false)
    private Profissional profissional;

    @Column(name = "data_hora", nullable = false, length = 30)
    private LocalDateTime dataHora;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "motivo", length = 255)
    private String motivo;

    public Agendamento(){
    }

    public void setIdAgendamento(Integer idAgendamento){
        this.idAgendamento = idAgendamento;


    }

    public Integer getIdAgendamento(){
        return idAgendamento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
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
