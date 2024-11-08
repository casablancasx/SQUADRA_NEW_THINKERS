package br.com.squadra.squadrajavabootcamp2024.modules.uf.model;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.model.MunicipioModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_uf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UfModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoUF;

    @Column(length = 2, unique = true, nullable = false)
    private String sigla;

    @Column(length = 50, unique = true)
    private String nome;

    @Column(nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "uf", cascade = CascadeType.ALL)
    private List<MunicipioModel> municipios;
}