package br.com.squadra.squadrajavabootcamp2024.dtos.update;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BairroUpdateDTO {

    @Digits(integer = 10, fraction = 0, message = "O código do bairro deve ser um número inteiro")
    @NotNull(message = "O código do bairro é obrigatório")
    private Long codigoBairro;

    @Digits(integer = 10, fraction = 0, message = "O código do município deve ser um número inteiro")
    @NotNull(message = "O código do município é obrigatório")
    private Long codigoMunicipio;

    @NotBlank(message = "O nome do bairro é obrigatório")
    @Pattern(regexp = "^[A-ZÁÀÂÃÉÈÊÍÌÎÓÒÔÕÚÙÛÇ\s]+$", message = "O nome do bairro deve conter apenas letras maiúsculas")
    private String nome;

    @Min(value = 1, message = "O status do bairro deve ser 1 ou 2")
    @Max(value = 2, message = "O status do bairro deve ser 1 ou 2")
    @NotNull(message = "O status do bairro é obrigatório")
    private Integer status;

}
