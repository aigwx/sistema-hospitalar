package br.com.hospital.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "enfermeiro")
public class Enfermeiro {

    @Id
    @Column(name = "id_profissional")
    private Integer idProfissional;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_profissional")
    private Profissional profissional;

    @Column(name = "coren", nullable = false, unique = true, length = 20)
    private String coren;

    public Enfermeiro() {
    }

    public Integer getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public String getCoren() {
        return coren;
    }

    public void setCoren(String coren) {
        this.coren = coren;
    }
}