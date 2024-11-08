package br.com.squadra.squadrajavabootcamp2024.modules.bairro.model;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.model.MunicipioModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_bairro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BairroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoBairro;

    @Column(unique = true)
    private String nome;

    private Integer status;

    @ManyToOne
    @JoinColumn(name = "codigoMunicipio")
    private MunicipioModel municipio;
}
