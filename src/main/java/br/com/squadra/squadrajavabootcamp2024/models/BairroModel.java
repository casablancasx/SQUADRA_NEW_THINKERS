package br.com.squadra.squadrajavabootcamp2024.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

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
