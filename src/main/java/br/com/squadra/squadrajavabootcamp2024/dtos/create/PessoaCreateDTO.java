package br.com.squadra.squadrajavabootcamp2024.dtos.create;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaCreateDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo sobrenome é obrigatório")
    private String sobrenome;

    @Digits(integer = 3, fraction = 0, message = "O campo idade deve ser um número inteiro")
    @NotNull(message = "O campo idade é obrigatório")
    private Integer idade;

    @NotBlank(message = "O campo login é obrigatório")
    private String login;

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;

    @NotNull(message = "O campo status é obrigatório")
    private Integer status;

    List<EnderecoCreateDTO> enderecos;


    @AssertTrue(message = "É obrigatório informar ao menos um endereço")
    public  boolean isEnderecosValid() {
        return enderecos != null && !enderecos.isEmpty();
    }
}
