package br.com.squadra.squadrajavabootcamp2024.dtos.update;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaUpdateDTO {

    @NotNull(message = "O campo códioPessoa é obrigatório.")
    private Long codigoPessoa;

    @NotBlank(message = "O campo nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo sobrenome é obrigatório.")
    private String sobrenome;

    @NotNull(message = "O campo idade é obrigatório.")
    private Integer idade;

    @NotBlank(message = "O campo login é obrigatório.")
    private String login;

    @NotBlank(message = "O campo senha é obrigatório.")
    private String senha;

    @NotNull(message = "O campo status é obrigatório.")
    private Integer status;

    private List<EnderecoUpdateDTO> enderecos;


    @AssertTrue(message = "O campo endereços é obrigatório.")
    public boolean isEnderecosValidos() {
        return enderecos != null && !enderecos.isEmpty();
    }
}
