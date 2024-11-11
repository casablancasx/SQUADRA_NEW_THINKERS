package br.com.squadra.squadrajavabootcamp2024.modules.bairro.model;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.model.MunicipioModel;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.model.EnderecoModel;
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
    private MunicipioModel municipio;

    @OneToMany(mappedBy = "bairro", cascade = CascadeType.ALL)
    private List<EnderecoModel> enderecos;
}
