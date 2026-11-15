package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotNull;

public class AssociacaoEspecialidadeDTO {

    @NotNull(message = "O médico é obrigatório")
    private Integer idProfissional;

    @NotNull(message = "A especialidade é obrigatória")
    private Integer idEspecialidade;

    public Integer getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }
}
