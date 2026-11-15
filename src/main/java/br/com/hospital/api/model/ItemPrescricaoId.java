
package br.com.hospital.api.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemPrescricaoId implements Serializable {

    private Integer idPrescricao;
    private Integer numeroItem;

    public ItemPrescricaoId() {}

    public ItemPrescricaoId(Integer idPrescricao, Integer numeroItem) {
        this.idPrescricao = idPrescricao;
        this.numeroItem = numeroItem;
    }

    public Integer getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Integer idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPrescricaoId that)) return false;
        return Objects.equals(idPrescricao, that.idPrescricao)
                && Objects.equals(numeroItem, that.numeroItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrescricao, numeroItem);
    }
}
