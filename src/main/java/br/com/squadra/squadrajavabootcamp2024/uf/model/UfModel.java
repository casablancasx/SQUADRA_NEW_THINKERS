package br.com.squadra.squadrajavabootcamp2024.uf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false, unique = true)
    private String sigla;

    @Column(length = 50, nullable = false, unique = true)
    private String nome;

    private Integer status;
}