package br.com.squadra.squadrajavabootcamp2024.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_municipio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoMunicipio;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "codigoUF")
    @JsonIgnore
    private UfModel uf;

    @OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BairroModel> bairros;


    @JsonProperty("codigoUF")
    public Long getCodigoUF() {
        return uf != null ? uf.getCodigoUF() : null;
    }
}
