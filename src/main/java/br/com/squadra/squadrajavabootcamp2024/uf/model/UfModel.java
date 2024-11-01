package br.com.squadra.squadrajavabootcamp2024.uf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
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

    @Column(unique = true)
    @NotNull(message = "O campo sigla é obrigatório")
    @Size(min = 2, max = 2, message = "A sigla deve ter 2 caracteres")
    @Pattern(regexp = "^[A-Z]{2}$", message = "A sigla deve conter apenas letras maiúsculas")
    private String sigla;

    @Column(length = 50, unique = true)
    @NotNull(message = "O campo nome é obrigatório")
    @Pattern(regexp = "^[A-ZÀ-Úa-zà-ú ]{1,50}$", message = "O nome deve conter apenas letras e espaços")
    private String nome;

    @NotNull(message = "O campo status é obrigatório")
    @Min(value = 0, message = "O status deve ser 0 ou 1")
    @Max(value = 1, message = "O status deve ser 0 ou 1")
    private Integer status;
}