package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto;

import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.model.EnderecoModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaResponseDTO {

    private Long codigoPessoa;

    private String nome;

    private String sobrenome;

    private Integer idade;

    private String login;

    private String senha;

    private Integer status;

    private List<EnderecoModel> enderecos;

}
