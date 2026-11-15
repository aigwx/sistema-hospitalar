
package br.com.hospital.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exame")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exame")
    private Integer idExame;

    @ManyToOne
    @JoinColumn(name = "id_atendimento", nullable = false)
    private Atendimento atendimento;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "data_solicitacao", nullable = false,
            insertable = false, updatable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_resultado")
    private LocalDateTime dataResultado;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    public Exame() {}

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
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
