package br.com.squadra.squadrajavabootcamp2024.dtos.update;


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
public class EnderecoUpdateDTO {

    private Long codigoEndereco;

    @NotNull(message = "O campo códigoPessoa é obrigatório.")
    private Long codigoPessoa;

    @NotNull(message = "O campo códigoBairro é obrigatório.")
    private Long codigoBairro;

    @NotBlank(message = "O campo nomeRua é obrigatório.")
    private String nomeRua;

    @NotBlank(message = "O campo numero é obrigatório.")
    private String numero;

    @NotBlank(message = "O campo complemento é obrigatório.")
    private String complemento;

    @NotBlank(message = "O campo cep é obrigatório.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O campo cep deve seguir o padrão 00000-000.")
    private String cep;

}
