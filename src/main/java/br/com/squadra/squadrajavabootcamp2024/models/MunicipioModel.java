package br.com.squadra.squadrajavabootcamp2024.models;

import jakarta.persistence.*;
import lombok.*;

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
    private UfModel uf;

    @OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL)
    private List<BairroModel> bairros;

}
