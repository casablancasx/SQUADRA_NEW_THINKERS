package br.com.squadra.squadrajavabootcamp2024.modules.municipio.model;

import br.com.squadra.squadrajavabootcamp2024.modules.bairro.model.BairroModel;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.model.UfModel;
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
