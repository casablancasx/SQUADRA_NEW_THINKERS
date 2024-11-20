package br.com.squadra.squadrajavabootcamp2024.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_bairro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BairroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoBairro;

    @Column(unique = true)
    private String nome;

    private Integer status;

    @ManyToOne
    @JoinColumn(name = "codigoMunicipio")
    @JsonIgnore
    private MunicipioModel municipio;

    @OneToMany(mappedBy = "bairro", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EnderecoModel> enderecos;


    @JsonProperty("codigoMunicipio")
    public Long getCodigoMunicipio() {
        return municipio != null ? municipio.getCodigoMunicipio() : null;
    }
}
