package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnfermeiroDTO {

    @NotNull(message = "O profissional é obrigatório")
    private Integer idProfissional;

    @NotBlank(message = "O COREN é obrigatório")
    private String coren;

    public Integer getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }

    public String getCoren() {
        return coren;
    }

    public void setCoren(String coren) {
        this.coren = coren;
    }
}