package br.com.squadra.squadrajavabootcamp2024.modules.municipio.model;

import br.com.squadra.squadrajavabootcamp2024.modules.uf.model.UfModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_municipio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MunicipioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoMunicipio;

    @ManyToOne
    @JoinColumn(name = "codigoUF")
    private UfModel uf;

    private String nome;

    private Integer status;
}
