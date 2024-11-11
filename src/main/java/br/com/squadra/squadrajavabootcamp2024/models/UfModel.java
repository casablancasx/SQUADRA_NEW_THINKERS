package br.com.squadra.squadrajavabootcamp2024.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
    @JsonIgnore
    private List<MunicipioModel> municipios;
}