package br.com.squadra.squadrajavabootcamp2024.dtos.create;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MunicipioCreateDTO {

    @NotNull(message = "O campo codigoUF é obrigatório")
    @Digits(integer = 10, fraction = 0, message = "O campo codigoUF deve conter apenas números inteiros")
    private Long codigoUF;

    @NotBlank(message = "O campo nome é obrigatório")
    @Pattern(regexp = "^[A-ZÁÀÂÃÉÈÊÍÌÎÓÒÔÕÚÙÛÇ\s]+$", message = "O campo nome deve conter apenas letras maiúsculas")
    private String nome;

    @NotNull(message = "O campo status é obrigatório")
    @Min(value = 1, message = "O campo status deve ser 1 ou 2")
    @Max(value = 2, message = "O campo status deve ser 1 ou 2")
    private Integer status;
}
