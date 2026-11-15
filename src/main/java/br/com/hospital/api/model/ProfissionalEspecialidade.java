package br.com.hospital.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profissional_especialidade")
public class ProfissionalEspecialidade {

    @EmbeddedId
    private ProfissionalEspecialidadeId id;

    @ManyToOne
    @MapsId("idProfissional")
    @JoinColumn(name = "id_profissional")
    private Medico medico;

    @ManyToOne
    @MapsId("idEspecialidade")
    @JoinColumn(name = "id_especialidade")
    private Especialidade especialidade;

    public ProfissionalEspecialidade() {
    }

    public ProfissionalEspecialidadeId getId() {
        return id;
    }

    public void setId(ProfissionalEspecialidadeId id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}