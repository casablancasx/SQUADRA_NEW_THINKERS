package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoCreateDTO {

    @NotNull(message = "O campo código do bairro é obrigatório")
    private Long codigoBairro;

    @NotBlank(message = "O campo nome da rua é obrigatório")
    private String nomeRua;

    @NotBlank(message = "O campo número é obrigatório")
    private String numero;

    @NotBlank(message = "O campo complemento é obrigatório")
    private String complemento;

    @NotBlank(message = "O campo CEP é obrigatório")
    @Pattern(regexp = "^\\d{2}\\d{3}[-]\\d{3}$", message = "O campo CEP deve seguir o padrão 00000-000")
    private String cep;

}
