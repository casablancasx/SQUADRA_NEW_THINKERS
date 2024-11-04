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
    @Min(value = 1, message = "O status deve ser 1 ou 2")
    @Max(value = 2, message = "O status deve ser 1 ou 2")
    private Integer status;

    @OneToMany(mappedBy = "uf", cascade = CascadeType.ALL)
    private List<MunicipioModel> municipios;
}