package br.com.squadra.squadrajavabootcamp2024.modules.uf.dto;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UfUpdateDTO {

    @NotNull(message = "O campo código é obrigatório")
    private Long codigoUF;

    @NotBlank(message = "O campo sigla é obrigatório")
    @Size(min = 2, max = 2, message = "A sigla deve ter 2 caracteres")
    @Pattern(regexp = "^[A-Z]{2}$", message = "A sigla deve conter apenas letras maiúsculas")
    private String sigla;


    @NotBlank(message = "O campo nome é obrigatório")
    @Pattern(regexp = "^[A-ZÀ-Úa-zà-ú ]{1,50}$", message = "O nome deve conter apenas letras e espaços")
    private String nome;

    @NotNull(message = "O campo status é obrigatório")
    @Min(value = 1, message = "O status deve ser 1 ou 2")
    @Max(value = 2, message = "O status deve ser 1 ou 2")
    private Integer status;

}
