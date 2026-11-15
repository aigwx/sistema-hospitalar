package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicoDTO {

    @NotNull(message = "O profissional é obrigatório")
    private Integer idProfissional;

    @NotBlank(message = "O CRM é obrigatório")
    private String crm;

    public Integer getIdProfissional(){
        return  idProfissional;
    }

    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
}
