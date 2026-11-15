package br.com.hospital.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable

public class ProfissionalEspecialidadeId implements Serializable{

    @Column(name = "id_profissional")
    private Integer idProfissional;

    @Column(name = "id_especialidade")
    private Integer idEspecialidade;

    public ProfissionalEspecialidadeId(){
    }

    public ProfissionalEspecialidadeId(Integer idProfissional, Integer idEspecialidade){
        this.idProfissional = idProfissional;
        this.idEspecialidade = idEspecialidade;
    }

    public Integer getIdProfissional(){
        return idProfissional;
    }

    public void setIdProfissional(Integer idProfissional){
        this.idProfissional = idProfissional;
    }

    public void setIdEspecialidade(Integer idEspecialidade){
        this.idEspecialidade = idEspecialidade;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof ProfissionalEspecialidadeId that)) return false;
        return Objects.equals(idProfissional, that.idProfissional) && Objects.equals(idEspecialidade, that.idEspecialidade);
    }

    @Override
    public int hashCode(){
        return Objects.hash(idProfissional, idEspecialidade);
    }
}
