package br.com.squadra.squadrajavabootcamp2024.dtos.create;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BairroCreateDTO {

    @NotNull(message = "O campo codigoMunicipio é obrigatório")
    @Digits(integer = 10, fraction = 0, message = "O campo codigoMunicipio deve conter apenas números")
    private Long codigoMunicipio;

    @Pattern(regexp = "^[A-ZÁÀÂÃÉÈÊÍÌÎÓÒÔÕÚÙÛÇ\\s]+$", message = "O campo nome deve conter apenas letras maiúsculas e não pode ser vazio")
    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotNull(message = "O campo status é obrigatório")
    @Min(value = 1, message = "O status deve ser 1 ou 2")
    @Max(value = 2, message = "O status deve ser 1 ou 2")
    private Integer status;

}
