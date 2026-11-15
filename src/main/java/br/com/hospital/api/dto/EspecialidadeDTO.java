package br.com.hospital.api.dto;

import jakarta.validation.constraints.NotBlank;

public class EspecialidadeDTO {

    private Integer idEspecialidade;

    @NotBlank(message = "O nome da especialidade é obrigatório")
    private String nome;

    private String descricao;

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}