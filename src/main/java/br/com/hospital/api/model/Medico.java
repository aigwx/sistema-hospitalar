package br.com.hospital.api.model;

import jakarta.persistence.*;

@Entity
@Table(name ="medico")
public class Medico {

    @Id
    @Column(name = "id_profissional")
    private Integer idProfissional;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_profissional")
    private Profissional profissional;

    @Column(name = "crm", nullable = false, unique = true, length = 20)
    private String crm;

    public Medico(){

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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
}
